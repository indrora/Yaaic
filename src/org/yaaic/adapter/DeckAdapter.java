/*
 Yaaic - Yet Another Android IRC Client

Copyright 2009 Sebastian Kaspari

This file is part of Yaaic.

Yaaic is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Yaaic is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Yaaic.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.yaaic.adapter;

import java.util.LinkedList;

import org.yaaic.model.Channel;
import org.yaaic.view.MessageListView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ListView;

/**
 * The adapter for the "DeckView"
 * 
 * @author Sebastian Kaspari <sebastian@yaaic.org>
 */
public class DeckAdapter extends BaseAdapter
{
	public static final String TAG = "Yaaic/DeckAdapter";
	
	private LinkedList<Channel> channels = new LinkedList<Channel>();
	private MessageListView currentView;
	private String currentChannel;
	
	/**
	 * Get number of item
	 */
	public int getCount()
	{
		return channels.size();
	}

	/**
	 * Get item at position
	 */
	public Channel getItem(int position)
	{
		if (position >= 0 && position < channels.size()) {
			return channels.get(position);
		}
		return null;
	}

	/**
	 * Get id of item at position
	 */
	public long getItemId(int position)
	{
		return position;
	}
	
	/**
	 * Add an item 
	 * 
	 * @param channel Name of the channel
	 * @param view The view object
	 */
	public void addItem(Channel channel)
	{
		channels.add(channel);
		
		notifyDataSetChanged();
	}
	
	/**
	 * Get an item by the channel's name
	 * 
	 * @param channel
	 * @return The item
	 */
	public int getPositionByName(String name)
	{
		for (int i = 0; i <  channels.size(); i++) {
			if (channels.get(i).getName().equals(name)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Remove an item
	 * 
	 * @param channel
	 */
	public void removeItem(Channel channel)
	{
		channels.remove(channel);
		
		notifyDataSetChanged();
	}
	
	/**
	 * Set single channel view
	 * 
	 * @param switched
	 */
	public void setSwitched(String channel, MessageListView current)
	{
		currentChannel = channel;
		currentView = current;
	}
	
	/**
	 * Get single channel view
	 * 
	 * @return
	 */
	public MessageListView getSwitchedView()
	{
		return currentView;
	}
	
	/**
	 * Get name of channel (single channel view)
	 * 
	 * @return
	 */
	public String getSwitchedName()
	{
		return currentChannel;
	}
	
	/**
	 * Has the view been switched to single channel view? 
	 * 
	 * @return view true if view is in single channel view, false otherwise
	 */
	public boolean isSwitched()
	{
		return currentView != null;
	}
	
	/**
	 * Get view at given position
	 */
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Channel channel = getItem(position);
		return renderChannel(channel, parent);
	}
	
	/**
	 * Render a channel view (MessageListView)
	 * 
	 * @param channel The channel of the view
	 * @param parent The parent view (context)
	 * @return The rendered MessageListView
	 */
	public MessageListView renderChannel(Channel channel, ViewGroup parent)
	{
		MessageListView list = new MessageListView(parent.getContext());
		list.setAdapter(new MessageListAdapter(channel, parent.getContext()));
		
		list.setDivider(null);
		list.setLayoutParams(new Gallery.LayoutParams(
			parent.getWidth() / 100 * 85,
			parent.getHeight() / 100 * 95
		));
		list.setBackgroundColor(0xff222222);
		list.setPadding(5, 5, 5, 5);
		list.setScrollContainer(false);
		list.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
		list.setSelection(list.getAdapter().getCount() - 1); // scroll to bottom
		
		return list;
	}
}