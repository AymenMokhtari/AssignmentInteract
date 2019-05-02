
package com.github.aymenmokthari;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;


        import com.github.aymenmokthari.model.Contact;

        import java.util.List;

/**
 * Created by Aymen on 10/28/2017.
 */

public class ContactAdapter  extends ArrayAdapter<Contact> {

    private int resourceId = 0;
    private LayoutInflater inflater;
    public Context mContext;

    public ContactAdapter(Context context, int resourceId, List<Contact> mediaItems) {
        super(context, 0, mediaItems);
        this.resourceId = resourceId;
        this.mContext = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    //ViewHolder Design Pattern
    static class ViewHolder {
        public TextView firstchar ,name, email;
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
        viewHolder.firstchar  = (TextView) rowView.findViewById(R.id.firstchar);
        viewHolder.name = (TextView) rowView.findViewById(R.id.name);
        viewHolder.email = (TextView) rowView.findViewById(R.id.email);
        rowView.setTag(viewHolder);

        //Affecter les données aux Views
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Contact contact = getItem(position);


        holder.firstchar.setText(Character.toString(contact.getFirstName().toUpperCase().charAt(0)));
        holder.name.setText(contact.getFirstName() +" "+contact.getLastName());
        holder.email.setText(contact.getEmail());
        notifyDataSetChanged();

        return rowView;
    }


}