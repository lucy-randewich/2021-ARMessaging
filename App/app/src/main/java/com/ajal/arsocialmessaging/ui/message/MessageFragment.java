package com.ajal.arsocialmessaging.ui.message;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ajal.arsocialmessaging.util.database.MessageService;
import com.ajal.arsocialmessaging.R;
import com.ajal.arsocialmessaging.util.database.ServiceGenerator;
import com.ajal.arsocialmessaging.databinding.FragmentMessageBinding;

import java.util.Arrays;
import java.util.List;

public class MessageFragment extends Fragment {

    private FragmentMessageBinding binding;

    // Components on fragment
    private List<String> messages;
    private ListView listView;
    private TextView postCodeInput;
    private Button sendBtn;
    private String messageSelected = "";
    private String postCode;


    private void addBannerToDatabase(String postcode, String message){
        // Set up connection for app to talk to database via rest controller
        MessageService service = ServiceGenerator.createService(MessageService.class);

        // TODO: Execute POST to add banner to server
    }

    /**
     * Enables/disables the send button, depending on postCode and messageSelected
     */
    private void setSendBtnAvailability(String text) {
        if(!text.isEmpty() && !messageSelected.isEmpty()){
            sendBtn.setEnabled(true);
        } else {
            sendBtn.setEnabled(false);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMessageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /** ListView code */
        // Fills the ListView with messages
        messages = Arrays.asList(getResources().getStringArray(R.array.messages));
        listView = root.findViewById(R.id.list_messagesToSend);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, messages);
        listView.setAdapter(adapter);

        // Sets a listener to figure out what item was clicked in list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                messageSelected = parent.getItemAtPosition(position).toString();
                String text = postCodeInput.getText().toString();
                setSendBtnAvailability(text);
            }
        });

        /** Postcode button code */
        postCodeInput = root.findViewById(R.id.text_input_postcode);
        sendBtn = root.findViewById(R.id.send_button);

        // Adds a listener to postcode text input to enable/disable button
        postCodeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setSendBtnAvailability(charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setSendBtnAvailability(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postCode = postCodeInput.getText().toString();
                Toast.makeText(getContext(), postCode+": "+messageSelected, Toast.LENGTH_SHORT).show();
                addBannerToDatabase(postCode, messageSelected);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}