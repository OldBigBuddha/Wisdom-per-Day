package kyoto.freeprojects.oldbigbuddha.wisdom_per_day;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import kyoto.freeprojects.oldbigbuddha.wisdom_per_day.databinding.FragmentDialogListenWisdomBinding;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by developer on 10/8/17.
 */

public class ListenAlertFragment extends DialogFragment {

    public interface ListenDialogInterface {
        void OnClickPositiveButton(String wisdom);
    }
    private ListenDialogInterface mListener;

    public void setListener(ListenDialogInterface listener) {
        mListener = listener;
    }

    private FragmentDialogListenWisdomBinding mBinding;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_dialog_listen_wisdom, null, false);
        mBinding = DataBindingUtil.bind(view);
        builder.setTitle("What wisdom did you get today?")
                .setView(mBinding.getRoot())
                .setPositiveButton("I got it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.OnClickPositiveButton(mBinding.etFieldWisdom.getText().toString());

                        getActivity().finish();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}
