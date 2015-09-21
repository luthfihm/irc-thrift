package com.pat;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by luthfi on 21/09/15.
 */
public class SimpleIrcHandler implements SimpleIrcService.Iface {

    private class Channel {
        public String name;
        public List<Integer> users = new ArrayList<Integer>();
        public List<String> message;
    }

    private int counter;

    private List<String> users;

    private List<Channel> channels;

    public SimpleIrcHandler()
    {
        users = new ArrayList<String>();
        channels = new ArrayList<Channel>();
        counter = 0;
    }

    @Override
    public String login() throws TException {
        String user = "user" + counter;
        users.add(user);
        counter++;
        System.out.println(user+" has logged in.");
        return user;
    }

    @Override
    public String nick(String name, String new_name) throws TException {
        if (users.contains(new_name)) {
            return "Nickname has been already used!";
        } else {
            int i = users.indexOf(name);
            users.set(i,new_name);
            System.out.println(name + " has changed to " + new_name + ".");
            return "OK";
        }
    }

    @Override
    public String join(String name, String channel) throws TException {
        boolean found = false;
        int i = 0;
        int indexOfChannel = 0;
        for (Channel channel1 : channels) {
            if (channel1.name.equals(channel))
            {
                found = true;
                indexOfChannel = i;
            }
            i++;
        }
        if (found)
        {
            Channel channel1 = channels.get(indexOfChannel);
            if (channel1.users.contains(users.indexOf(name)))
            {
                return "You already join channel " +  channel;
            } else {
                channel1.users.add(users.indexOf(name));
                channels.set(indexOfChannel, channel1);
                System.out.println(name + " has joined channel " + channel + ".");
                return "OK";
            }
        }
        else
        {
            Channel channel1 = new Channel();
            channel1.name = channel;
            channel1.users.add(users.indexOf(name));
            channels.add(channel1);
            System.out.println(name + " has created channel " + channel + ".");
            return "OK";
        }
    }

    @Override
    public String leave(String name, String channel) throws TException {
        return null;
    }

    @Override
    public String exit(String name) throws TException {
        users.remove(name);
        System.out.println(name + " has exited.");
        return "OK";
    }

    @Override
    public String sendToAllChannel(String name, String message) throws TException {
        return null;
    }

    @Override
    public String sendToChannel(String name, String channel, String message) throws TException {
        boolean isChannelExist = false;
        boolean isMember = false;
        int i = 0;
        int indexOfChannel = 0;
        for (Channel channel1 : channels) {
            if (channel1.name.equals(channel))
            {
                isChannelExist = true;
                if (channel1.users.contains(users.indexOf(name))) {
                    isMember = true;
                    indexOfChannel = i;
                }
            }
            i++;
        }
        if (isChannelExist && isMember)
        {
            Channel channel1 = channels.get(indexOfChannel);
            channel1.message.add("[" + channel + "]" + "(" + name + ") " + message);
            return "OK";
        }
        else if (!isChannelExist)
        {
            return "Channel " + channel + "does not exist.";
        }
        else if (!isMember)
        {
            return "You are not the member of channel " + channel + ".";
        }
        return null;
    }

    @Override
    public List<String> getMessage(String name, String channel) throws TException {
        return null;
    }
}
