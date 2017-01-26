package ru.ebi.romaprepod.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import ru.ebi.romaprepod.R;
import ru.ebi.romaprepod.databinding.ActivityMainBinding;
import ru.ebi.romaprepod.ui.fragment.LabsFragment;
import ru.ebi.romaprepod.ui.fragment.LecturesFragment;
import ru.ebi.romaprepod.ui.fragment.ProfileFragment;
import ru.ebi.romaprepod.ui.fragment.ResultsFragment;
import ru.ebi.romaprepod.ui.fragment.TestsFragment;

public class MainActivity extends BaseActivity {

	private static final String SAVED_SECTION = "saved_section";

	public static Intent newIntent(Context context) {
		return new Intent(context, MainActivity.class);
	}

	private ActivityMainBinding binding;

	private int currentSection = R.id.section_lectures;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		initToolbar(binding.toolbar);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, new LecturesFragment())
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
					.commit();
			supportInvalidateOptionsMenu();
		} else {
			currentSection = savedInstanceState.getInt(SAVED_SECTION, R.id.section_lectures);
			binding.navigation.getMenu().findItem(currentSection).setChecked(true);
		}

		binding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				Fragment fragmentOld = getContentFragment();
				Fragment fragmentNew = null;
				switch (item.getItemId()) {
					case R.id.section_lectures:
						if (!(fragmentOld instanceof LecturesFragment)) {
							fragmentNew = new LecturesFragment();
						}
						break;
					case R.id.section_labs:
						if (!(fragmentOld instanceof LabsFragment)) {
							fragmentNew = new LabsFragment();
						}
						break;
					case R.id.section_tests:
						if (!(fragmentOld instanceof TestsFragment)) {
							fragmentNew = new TestsFragment();
						}
						break;
					case R.id.section_results:
						if (!(fragmentOld instanceof ResultsFragment)) {
							fragmentNew = new ResultsFragment();
						}
						break;
					case R.id.section_profile:
						if (!(fragmentOld instanceof ProfileFragment)) {
							fragmentNew = new ProfileFragment();
						}
						break;
				}
				if (fragmentNew != null) {
					currentSection = item.getItemId();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.fragment_container, fragmentNew)
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
							.commit();
					supportInvalidateOptionsMenu();
					return true;
				}
				return false;
			}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(SAVED_SECTION, currentSection);
	}

	private Fragment getContentFragment() {
		return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
	}
}