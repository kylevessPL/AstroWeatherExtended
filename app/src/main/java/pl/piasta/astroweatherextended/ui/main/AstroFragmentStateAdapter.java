package pl.piasta.astroweatherextended.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Arrays;
import java.util.List;

import pl.piasta.astroweatherextended.ui.base.BaseFragment;

public class AstroFragmentStateAdapter extends FragmentStateAdapter {

    private final List<BaseFragment> mFragmentList;

    public AstroFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        mFragmentList = Arrays.asList(new SunFragment(), new MoonFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

    @NonNull
    public String getItemName(int position) {
        return mFragmentList.get(position).getFragmentName();
    }
}
