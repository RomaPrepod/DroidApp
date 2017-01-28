package ru.ebi.romaprepod.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import ru.ebi.romaprepod.R;
import ru.ebi.romaprepod.databinding.FragmentProfileBinding;
import ru.ebi.romaprepod.repository.UserManager;
import ru.ebi.romaprepod.util.Blogger;

public class ProfileFragment extends BaseFragment {

	private FragmentProfileBinding binding;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		binding.text.setText(String.format(Locale.US, "getId: %s\ngetIssuer: %s\ngetSignature: %s\ngetSubject: %s\ngetAudience: %s\ngetHeader: %s\ngetClaim: %s\n",
				UserManager.getUser().getId(),
				UserManager.getUser().getIssuer(),
				UserManager.getUser().getSignature(),
				UserManager.getUser().getSubject(),
				Blogger.listToString(UserManager.getUser().getAudience()),
				Blogger.listToString(UserManager.getUser().getHeader().entrySet()),
				UserManager.getUser().getClaim("email").asString()
		));
	}

	private void load() {
		// TODO
	}
}