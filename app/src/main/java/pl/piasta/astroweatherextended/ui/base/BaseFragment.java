package pl.piasta.astroweatherextended.ui.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @NonNull
    public abstract String getFragmentName();

}
