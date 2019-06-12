package com.softmarrow.gamelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BoxAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Game> games;

    BoxAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item, parent, false);
        }

        Game game = getGame(position);

        ((TextView) view.findViewById(R.id.name)).setText(game.getName());
        ((TextView) view.findViewById(R.id.realPrice)).setText(game.getRealPrice());
        ((TextView) view.findViewById(R.id.discountPrice)).setText(game.getDiscountPrice());

        return view;
    }

    private Game getGame(int position) {
        return (Game) getItem(position);
    }
}
