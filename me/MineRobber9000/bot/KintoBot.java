package me.MineRobber9000.bot;

import java.util.*;

import org.jibble.pircbot.*;

public class KintoBot extends PircBot{
	
	private boolean jokesPub = false;
	private String password = "";
	private String BotOP = "";
	private String BotOP2 = "";
	private ArrayList<String> mods = new ArrayList<String>();
	
	public KintoBot(String name, String pass) {
		this.setName(name);
		this.password = pass;
	}
	
	public boolean checkMod(String user) {
		if (mods.contains(user)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if (message.equalsIgnoreCase("!goaway")) {
			if (sender.equals(BotOP) || sender.equals(BotOP2)) {
				partChannel(channel, "Fine, I'll leave quietly.");
			} else {
				sendMessage(channel, "Only the main operator can use this command.");
			}
		}
		if (message.contains("!game")) {
			String[] parts = message.split(" ");
			if (parts[1].equalsIgnoreCase("genesis")) {
				if (parts[2].equals("1")) {
					sendMessage(channel, "Name: Sonic the Hedgehog; Release date: 1991-06-23;");
				}
				if (parts[2].equals("2")) {
					sendMessage(channel, "Name: Sonic the Hedgehog 2; Release date: 1992-11-24;");
				}
				if (parts[2].equals("3")) {
					sendMessage(channel, "Name: Sonic the Hedgehog 3 (and Knuckles); Release date: 1994-02-02 (1994-08-18)");
				}
				if (parts[2].equalsIgnoreCase("spinball")) {
					sendMessage(channel, "Name: Sonic Spinball; Initial US Release date: 1993-11-23;");
				}
				if (parts[2].equalsIgnoreCase("3dblast")) {
					sendMessage(channel, "Name: Sonic 3D Blast; Genesis release date: 1996-11-12;");
				}
			}
			if (parts[1].equalsIgnoreCase("saturn")) {
				if (parts[2].equalsIgnoreCase("r")) {
					sendMessage(channel, "Name: Sonic R; Release date: 1997-11-18;");
				}
				if (parts[2].equalsIgnoreCase("3dblast")) {
					sendMessage(channel, "Name: Sonic 3D Blast; Saturn release date: 1996-11-20;");
				}
			}
			if (parts[1].equalsIgnoreCase("dreamcast")) {
				if (parts[2].equalsIgnoreCase("adventure")) {
					if (parts.length == 4) {
						if (parts[3].equals("2")) {
							sendMessage(channel, "Name: Sonic Adventure 2; Release date: 2001-06-19;");
						}
					} else {
						sendMessage(channel, "Name: Sonic Adventure; Release date: 1998-12-23;");
					}
				}
				if (parts[2].equalsIgnoreCase("shuffle")) {
					sendMessage(channel, "Name: Sonic Shuffle; Release date: 2000-11-14;");
				}
			}
		}
		if (message.equalsIgnoreCase("!didit")) {
			if (jokesPub) {
				sendMessage(channel, "I DID IT! \\o/");
			} else {
				if (sender.equals(BotOP) || sender.equalsIgnoreCase(BotOP2) || checkMod(sender)) {
					sendMessage(channel, "I DID IT! \\o/");
				} else {
					return;
				}
			}
		}
		if (message.equalsIgnoreCase("!isayhey")) {
			if (channel.equals("")) {
				return;
			} else {
				if (jokesPub) {
					sendMessage(channel, "What's going on?");
				} else {
					if (sender.equals(BotOP) || sender.equals("BotOP2") || checkMod(sender)) {
						sendMessage(channel, "What's going on?");
					} else {
						return;
					}
				}
			}
		}
		if (message.equals("!jokes")) {
			if (sender.equals(BotOP) || sender.equals(BotOP2) || checkMod(sender)) {
				jokesPub = !jokesPub;
				sendMessage(channel, "Joke commands toggled!");
			} else {
				sendMessage(channel, "Only the current operator can toggle joke commands!");
			}
		}
		if (message.equalsIgnoreCase("!source")) {
			sendMessage(channel, sender + ": My source code is at http://www.github.com/MineRobber9000/KintoBot");
			sendMessage(channel, sender + ": It may not be up to date though, my creator works on my code a lot.");
		}
		if (message.contains("!kb")) {
			if (sender.equals(BotOP)) {
				String[] parts = message.split(" ");
				if (parts[1].equalsIgnoreCase("dumpvars")) {
					sendMessage(channel, "The sender is " + sender + ", the channel was " + channel + ", the login variable was " + login + ", the hostname is " + hostname + ", and the message was " + message + ".");
				}
				if (parts[1].equalsIgnoreCase("botop2")) {
					if (parts[2].equalsIgnoreCase("clear")) {
						BotOP2 = "";
					} else {
						BotOP2 = parts[2];
					}
				}
				if (parts[1].equalsIgnoreCase("mods")) {
					if (parts.length == 4) {
						switch (parts[2]) {
						case "add": mods.add(parts[3]);
						            break;
						case "rm": mods.remove(parts[3]);
						           break;
						}
					} else {
						if (parts[2].equalsIgnoreCase("wipe")) {
							mods = new ArrayList<String>();
						} else {
							sendMessage(channel, "Mods: " + mods.toString());
						}
					}
				
				if (parts[2].equalsIgnoreCase("check")) {
					if (checkMod(parts[3])) {
						sendMessage(channel, BotOP + ": Yes, " + parts[3] + " is a mod.");
					} else {
						sendMessage(channel, BotOP + ": No, " + parts[3] + " is not a mod.");
					}
				}
			}
			if (parts[1].equalsIgnoreCase("passop")) {
				BotOP = parts[2];
			} 
			} else {
				sendMessage(channel, "Only the current main operator can configure KintoBot.");
			}
		}
}
	
	protected  void	onPrivateMessage(String sender, String login, String hostname, String message) {
		if (message.contains("login")) {
			String[] parts = message.split(" ");
			if (parts[1].equals(password)) {
				BotOP = sender;
				sendMessage(sender, "You have been authorized.");
			} else {
				sendMessage(sender, "Login failed: wrong password");
			}
		}
		if (message.contains("join")) {
			if (sender.equals(BotOP) || sender.equals(BotOP2)) {
				String[] parts = message.split(" ");
				joinChannel(parts[1]);
			}
		}
	}
}
