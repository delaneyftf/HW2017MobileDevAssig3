package com.dramalho.hw2017mobiledev.howardchat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);


        final ListView mListView = v.findViewById(R.id.list_view);
        final EditText mEditText = v.findViewById(R.id.editText);
        Button mButton = v.findViewById(R.id.button);

        MessageSource.get(getContext()).getMessages(new MessageSource.MessageListener(){
            @Override
            public void onMessageRecieved(List<Message> messageList){
                MessageArrayAdapter adapter = new MessageArrayAdapter(getContext(), messageList);
                mListView.setAdapter(adapter);
                mListView.setSelection(adapter.getCount()-1);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                Message message = new Message(user.getDisplayName(), user.getUid(), mEditText.getText().toString());
                MessageSource.get(getContext()).sendMessage(message);

                mEditText.setText(" ");
            }

        });

        return v;
    }

    private class MessageArrayAdapter extends BaseAdapter{
        protected Context mContext;
        protected List<Message> mMessageList;
        protected LayoutInflater mLayoutInflater;
        public MessageArrayAdapter(Context context, List<Message> messageList){
            mContext = context;
            mMessageList = messageList;
            mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount(){
            return mMessageList.size();
        }

        @Override
        public Object getItem(int position){
            return position;
        }
        @Override
        public long getItemId(int position){
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            final Message message = mMessageList.get(position);
            View rowView = mLayoutInflater.inflate(R.layout.list_view_item, parent, false);

            TextView username = rowView.findViewById(R.id.user_text_view);
            username.setText(message.getmUserName());

            TextView content = rowView.findViewById(R.id.content);
            content.setText(message.getmContent());

            return rowView;

        }
    }
}