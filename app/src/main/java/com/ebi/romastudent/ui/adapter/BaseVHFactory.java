package com.ebi.romastudent.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class BaseVHFactory<VH extends RecyclerView.ViewHolder> implements VHFactory<VH> {

	private VHConstructor<VH> vhConstructor;

	public BaseVHFactory(Class<VH> type) {
		try {
			Constructor<VH> parentConstructor = type.getConstructor(ViewGroup.class);
			vhConstructor = new ParentVHConstructor<>(parentConstructor);
		} catch (NoSuchMethodException e) {
			try {
				Constructor<VH> contextConstructor = type.getConstructor(Context.class);
				vhConstructor = new ContextVHConstructor<>(contextConstructor);
			} catch (NoSuchMethodException e1) {
				throw new IllegalArgumentException("no suitable constructor found for " + type.getName());
			}
		}
	}

	@Override
	public VH onCreateViewHolder(ViewGroup parent) {
		return vhConstructor.construct(parent);
	}

	private interface VHConstructor<VH> {
		VH construct(ViewGroup parent);
	}

	private static class ContextVHConstructor<VH> implements VHConstructor<VH> {

		public final Constructor<VH> constructor;

		public ContextVHConstructor(Constructor<VH> constructor) {
			this.constructor = constructor;
		}

		@Override
		public VH construct(ViewGroup parent) {
			try {
				return constructor.newInstance(parent.getContext());
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static class ParentVHConstructor<VH> implements VHConstructor<VH> {
		public final Constructor<VH> constructor;

		public ParentVHConstructor(Constructor<VH> constructor) {
			this.constructor = constructor;
		}

		@Override
		public VH construct(ViewGroup parent) {
			try {
				return constructor.newInstance(parent);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
