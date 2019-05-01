
package com.github.aymenmokthari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.aymenmokthari.model.Contact;
import com.github.aymenmokthari.model.ListContact;

import java.util.List;

/**
 * Created by Aymen on 10/28/2017.
 */

public class ListAdapter extends ArrayAdapter<ListContact> {

    private int resourceId = 0;
    private LayoutInflater inflater;
    public Context mContext;

    public ListAdapter(Context context, int resourceId, List<ListContact> mediaItems) {
        super(context, 0, mediaItems);
        this.resourceId = resourceId;
        this.mContext = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    //ViewHolder Design Pattern
    static class ViewHolder {
        public TextView listname, contactsCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        //Réutiliser les Views
        if (rowView == null) {
            rowView = inflater.inflate(resourceId, parent, false);
        }

        //Configuration du ViewHolder
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.listname = (TextView) rowView.findViewById(R.id.listname);
        viewHolder.contactsCount = (TextView) rowView.findViewById(R.id.contactsCount);
        rowView.setTag(viewHolder);

        //Affecter les données aux Views
        ViewHolder holder = (ViewHolder) rowView.getTag();
        ListContact listContact = getItem(position);

        holder.listname.setText(listContact.getName());
        holder.contactsCount.setText(String.valueOf(listContact.getContactsCount()));
        notifyDataSetChanged();

        return rowView;
    }


}