package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class FamilieboblaSamtaleFragment extends Fragment {

    public FamilieboblaSamtaleFragment() {}

    Database database;
    SharedPreferences sharedPreferences;

    private int samtaleId;
    private String samtaleName, samtaleTo;
    private ArrayList<FamilieboblaSamtaleModel> messages;

    private TextView samtaleTitle, messageToSend, brukerNavn;
    private Button sendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_familiebobla_samtale, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        // Henter argumenter som ble sendt via bundle
        setSamtaleId(Integer.parseInt(getArguments().getString("samtaleId")));
        setSamtaleName(getArguments().getString("samtaleName"));
        setSamtaleTo(getArguments().getString("samtaleTo"));

        // Setter samtalenavn og hvem det er en samtale med
        samtaleTitle = view.findViewById(R.id.Samtalenavn);
        samtaleTitle.setText(samtaleName);

        String samtaleMed = "Samtale med: " + samtaleTo;
        brukerNavn = view.findViewById(R.id.brukerNavn);
        brukerNavn.setText(samtaleMed);

        messageToSend = view.findViewById(R.id.familieboblaSamtale_messageToSend);
        sendButton = view.findViewById(R.id.familieboblaSamtale_sendMessageBtn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // Henter meldinger og viser de i RecyclerView
        setMessagesInConversation();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView familieboblaSamtaleRecyclerView = getView().findViewById(R.id.samtaleDisplay);
        familieboblaSamtaleRecyclerView.setAdapter(new FamilieboblaSamtaleAdapter(getContext(), messages));

        familieboblaSamtaleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Går til bunnen av samtalen, slik at man slipper å scrolle til de siste meldingene
        familieboblaSamtaleRecyclerView.scrollToPosition(messages.size() - 1);
    }

    private void setMessagesInConversation() {
        // Getting data from database table MESSAGES
        Cursor messages = database.getAllMessageFromConversation(samtaleId);

        ArrayList<FamilieboblaSamtaleModel> allMessages = new ArrayList<>();

        while(messages.moveToNext()) {
            int messageID = Integer.parseInt(messages.getString(messages.getColumnIndex(Database.COLUMN_ID)));
            int conversationID = Integer.parseInt(messages.getString(messages.getColumnIndex(Database.COLUMN__MESSAGE_PART_OF_CONVERSATIONID)));
            int fromID = Integer.parseInt(messages.getString(messages.getColumnIndex(Database.COLUMN__MESSAGE_USER_FROM)));
            String message = messages.getString(messages.getColumnIndex(Database.COLUMN__MESSAGE_TEXT));

            // Dersom samtaleID fra database og lokalt er like vil den bli lagt til i array
            if (samtaleId == conversationID) {
                FamilieboblaSamtaleModel message2 = new FamilieboblaSamtaleModel(messageID, fromID, conversationID, message);
                allMessages.add(message2);
            }
        }
        this.messages = allMessages;
    }

    // Legger en melding til i databasen og henter meldingene på nytt
    private void sendMessage() {
        int meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

        long addToDatabase = -1;

        if (!messageToSend.getText().toString().isEmpty())
            addToDatabase = database.sendMessage(samtaleId, meID, messageToSend.getText().toString());

        if (addToDatabase >= 0) {
            messageToSend.setText("");
            setMessagesInConversation();
            setUpRecyclerView();
        } else {
            Toast.makeText(getContext(),"Kunne ikke sende meldingen: " + messageToSend.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setSamtaleId(int samtaleId) {
        this.samtaleId = samtaleId;
    }

    public void setSamtaleName(String samtaleName) {
        this.samtaleName = samtaleName;
    }

    public void setSamtaleTo(String samtaleTo) {
        this.samtaleTo = samtaleTo;
    }
}