package me.messageofdeath.paidranks.commands;

import me.messageofdeath.paidranks.database.language.LanguageSettings;
import me.messageofdeath.paidranks.PaidRanks;
import me.messageofdeath.paidranks.utils.api.PaidRanksAPI;
import me.messageofdeath.paidranks.utils.laddermanager.Ladder;
import me.messageofdeath.paidranks.utils.laddermanager.LadderManager;
import me.messageofdeath.paidranks.utils.rankmanager.Rank;
import me.messageofdeath.paidranks.utils.zrequired.commands.Command;
import me.messageofdeath.paidranks.utils.zrequired.commands.IssuedCommand;
import me.messageofdeath.paidranks.utils.zrequired.commands.MessageCommand;
import me.messageofdeath.paidranks.utils.zrequired.commands.Messenger;
import me.messageofdeath.paidranks.utils.zrequired.pagelists.Option;
import me.messageofdeath.paidranks.utils.zrequired.pagelists.PageList;

import org.bukkit.ChatColor;

public class PaidRanksCommand extends MessageCommand {
	
	private PaidRanks instance;
	private LadderManager manager;
	private PageList list;

	public PaidRanksCommand(PaidRanks instance) {
		this.instance = instance;
		this.list = new PageList(4);
		setupHelp();
		this.messenger = new Messenger(this.instance.getPrefix());
	}

	@Command(name = "paidranks")
	public void issue(IssuedCommand cmd) {
		this.manager = this.instance.getLadderManager();
		if (cmd.getLength() == 0) {
			help(cmd, 0);
		} else if (cmd.getLength() == 1) {
			if (cmd.getArg(0).equalsIgnoreCase("reload")) {
				reloadPlugin(cmd);
			} else if (cmd.getArg(0).equalsIgnoreCase("ladder")
					|| cmd.getArg(0).equalsIgnoreCase("rank")) {
				super.wrongArgs(cmd);
			} else if (cmd.getArg(0).equalsIgnoreCase("help")) {
				help(cmd, 0);
			} else {
				help(cmd, 0);
			}
		} else if (cmd.getLength() == 2) {
			if (cmd.getArg(0).equalsIgnoreCase("help")) {
				if (cmd.isNumeric(1)) {
					help(cmd, cmd.getInteger(1));
				} else {
					super.error(cmd, LanguageSettings.Commands_MustBeNumeric.getSetting().replace("%arg", "page number"));
				}
			} else if (cmd.getArg(0).equalsIgnoreCase("reload")) {
				if (cmd.getArg(1).equalsIgnoreCase("language")) {
					reloadLanguage(cmd);
				} else if (cmd.getArg(1).equalsIgnoreCase("ranks")) {
					reloadRanks(cmd);
				} else {
					help(cmd, 0);
				}
			} else if (cmd.getArg(0).equalsIgnoreCase("ladder")) {
				if (cmd.getArg(1).equalsIgnoreCase("create")
						|| cmd.getArg(1).equalsIgnoreCase("remove")
						|| cmd.getArg(1).equalsIgnoreCase("set")
						|| cmd.getArg(1).equalsIgnoreCase("toggle")
						|| cmd.getArg(1).equalsIgnoreCase("info")) {
					super.wrongArgs(cmd);
				} else if (cmd.getArg(1).equalsIgnoreCase("list")) {
					listLadders(cmd);
				} else {
					help(cmd, 0);
				}
			} else if (cmd.getArg(0).equalsIgnoreCase("rank")) {
				if (cmd.getArg(1).equalsIgnoreCase("add")
						|| cmd.getArg(1).equalsIgnoreCase("remove")
						|| cmd.getArg(1).equalsIgnoreCase("set")
						|| cmd.getArg(1).equalsIgnoreCase("list")
						|| cmd.getArg(1).equalsIgnoreCase("info")) {
					super.wrongArgs(cmd);
				} else {
					help(cmd, 0);
				}
			} else {
				help(cmd, 0);
			}
		} else if (cmd.getLength() == 3) {
			if (cmd.getArg(0).equalsIgnoreCase("reload")) {
				if (cmd.getArg(1).equalsIgnoreCase("language")
						|| cmd.getArg(1).equalsIgnoreCase("ranks")) {
					super.wrongArgs(cmd);
				} else {
					help(cmd, 0);
				}
			} else if (cmd.getArg(0).equalsIgnoreCase("ladder")) {
				if (cmd.getArg(1).equalsIgnoreCase("create")) {
					this.createLadder(cmd, cmd.getArg(2), null, null);
				} else if (cmd.getArg(1).equalsIgnoreCase("remove")) {
					removeLadder(cmd, cmd.getArg(2));
				} else if (cmd.getArg(1).equalsIgnoreCase("set")
						|| cmd.getArg(1).equalsIgnoreCase("toggle")) {
					super.wrongArgs(cmd);
				} else if (cmd.getArg(1).equalsIgnoreCase("list")) {
					listLadders(cmd);
				} else if (cmd.getArg(1).equalsIgnoreCase("info")) {
					infoLadder(cmd, cmd.getArg(2));
				} else {
					help(cmd, 0);
				}
			} else if (cmd.getArg(0).equalsIgnoreCase("rank")) {
				if (cmd.getArg(1).equalsIgnoreCase("add")
						|| cmd.getArg(1).equalsIgnoreCase("remove")
						|| cmd.getArg(1).equalsIgnoreCase("info")) {
					super.wrongArgs(cmd);
				} else if (cmd.getArg(1).equalsIgnoreCase("list")) {
					listRanks(cmd, cmd.getArg(2));
				} else {
					help(cmd, 0);
				}
			} else {
				help(cmd, 0);
			}
		} else if (cmd.getLength() == 4) {
			if (cmd.getArg(0).equalsIgnoreCase("reload")) {
				if (cmd.getArg(1).equalsIgnoreCase("language")
						|| cmd.getArg(1).equalsIgnoreCase("ranks")) {
					super.wrongArgs(cmd);
				} else {
					help(cmd, 0);
				}
			} else if (cmd.getArg(0).equalsIgnoreCase("ladder")) {
				if (cmd.getArg(1).equalsIgnoreCase("create")) {
					this.createLadder(cmd, cmd.getArg(2), cmd.getArg(3), null);
				} else if (cmd.getArg(1).equalsIgnoreCase("remove")) {
					this.removeLadder(cmd, cmd.getArg(2));
				} else if (cmd.getArg(1).equalsIgnoreCase("set")) {
					this.setLadder(cmd, cmd.getArg(2), cmd.getArg(3), null);
				} else if (cmd.getArg(1).equalsIgnoreCase("list")) {
					this.listLadders(cmd);
				} else if(cmd.getArg(1).equalsIgnoreCase("toggle")) {
					if(cmd.getArg(2).equalsIgnoreCase("requiresRank")) {
						this.toggleRequiresRank(cmd, cmd.getArg(3));
					}else{
						super.wrongArgs(cmd);
					}
				}else if (cmd.getArg(1).equalsIgnoreCase("info")) {
					infoLadder(cmd, cmd.getArg(2));
				}else{
					help(cmd, 0);
				}
			} else if (cmd.getArg(0).equalsIgnoreCase("rank")) {
				if (cmd.getArg(1).equalsIgnoreCase("add")) {
					this.addRank(cmd, cmd.getArg(2), cmd.getArg(3), 0, "noPerm");
				} else if (cmd.getArg(1).equalsIgnoreCase("remove")) {
					removeRank(cmd, cmd.getArg(2), cmd.getArg(3));
				} else if (cmd.getArg(1).equalsIgnoreCase("set")) {
					super.wrongArgs(cmd);
				} else if (cmd.getArg(1).equalsIgnoreCase("list")) {
					this.listRanks(cmd, cmd.getArg(2));
				} else if (cmd.getArg(1).equalsIgnoreCase("info")) {
					this.infoRank(cmd, cmd.getArg(2), cmd.getArg(3));
				} else {
					help(cmd, 0);
				}
			} else {
				help(cmd, 0);
			}
		} else if (cmd.getLength() == 5) {
			if (cmd.getArg(0).equalsIgnoreCase("reload")) {
				if (cmd.getArg(1).equalsIgnoreCase("language")
						|| cmd.getArg(1).equalsIgnoreCase("ranks")) {
					super.wrongArgs(cmd);
				} else {
					help(cmd, 0);
				}
			} else if (cmd.getArg(0).equalsIgnoreCase("ladder")) {
				if (cmd.getArg(1).equalsIgnoreCase("create")) {
					this.createLadder(cmd, cmd.getArg(2), cmd.getArg(3), cmd.getArg(4));
				} else if (cmd.getArg(1).equalsIgnoreCase("remove")) {
					this.removeLadder(cmd, cmd.getArg(2));
				} else if (cmd.getArg(1).equalsIgnoreCase("set")) {
					this.setLadder(cmd, cmd.getArg(2), cmd.getArg(3), cmd.getArg(4));
				} else if (cmd.getArg(1).equalsIgnoreCase("list")) {
					this.listLadders(cmd);
				} else if(cmd.getArg(1).equalsIgnoreCase("toggle")) {
					if(cmd.getArg(2).equalsIgnoreCase("requiresRank")) {
						this.toggleRequiresRank(cmd, cmd.getArg(3));
					}else{
						super.wrongArgs(cmd);
					}
				}else if (cmd.getArg(1).equalsIgnoreCase("info")) {
					infoLadder(cmd, cmd.getArg(2));
				}else {
					help(cmd, 0);
				}
			} else if (cmd.getArg(0).equalsIgnoreCase("rank")) {
				if (cmd.getArg(1).equalsIgnoreCase("add")) {
					if(cmd.isNumeric(4)) {
						this.addRank(cmd, cmd.getArg(2), cmd.getArg(3), cmd.getDouble(4), "noPerm");
					}else{
						super.error(cmd, LanguageSettings.Commands_MustBeNumeric.getSetting().replace("%arg", "price"));
					}
				} else if (cmd.getArg(1).equalsIgnoreCase("remove")) {
					this.removeRank(cmd, cmd.getArg(2), cmd.getArg(3));
				} else if (cmd.getArg(1).equalsIgnoreCase("list")) {
					this.listRanks(cmd, cmd.getArg(2));
				} else if (cmd.getArg(1).equalsIgnoreCase("set")){
					super.wrongArgs(cmd);
				} else if (cmd.getArg(1).equalsIgnoreCase("info")) {
					this.infoRank(cmd, cmd.getArg(2), cmd.getArg(3));
				}else{
					help(cmd, 0);
				}
			} else {
				help(cmd, 0);
			}
		} else if (cmd.getLength() == 6) {
			if (cmd.getArg(0).equalsIgnoreCase("reload")) {
				if (cmd.getArg(1).equalsIgnoreCase("language")
						|| cmd.getArg(1).equalsIgnoreCase("ranks")) {
					super.wrongArgs(cmd);
				} else {
					help(cmd, 0);
				}
			} else if (cmd.getArg(0).equalsIgnoreCase("ladder")) {
				if (cmd.getArg(1).equalsIgnoreCase("create")) {
					this.createLadder(cmd, cmd.getArg(2), cmd.getArg(3), cmd.getArg(4));
				} else if (cmd.getArg(1).equalsIgnoreCase("remove")) {
					this.removeLadder(cmd, cmd.getArg(2));
				} else if (cmd.getArg(1).equalsIgnoreCase("set")) {
					this.setLadder(cmd, cmd.getArg(2), cmd.getArg(3), cmd.getArg(4));
				} else if (cmd.getArg(1).equalsIgnoreCase("list")) {
					this.listLadders(cmd);
				} else if(cmd.getArg(1).equalsIgnoreCase("toggle")) {
					if(cmd.getArg(2).equalsIgnoreCase("requiresRank")) {
						this.toggleRequiresRank(cmd, cmd.getArg(3));
					}else{
						super.wrongArgs(cmd);
					}
				}else if (cmd.getArg(1).equalsIgnoreCase("info")) {
					infoLadder(cmd, cmd.getArg(2));
				}else {
					help(cmd, 0);
				}
			} else if (cmd.getArg(0).equalsIgnoreCase("rank")) {
				if (cmd.getArg(1).equalsIgnoreCase("add")) {
					if (cmd.isNumeric(4)) {
						addRank(cmd, cmd.getArg(2), cmd.getArg(3), cmd.getDouble(4), cmd.getArg(5));
					} else {
						super.error(cmd, LanguageSettings.Commands_MustBeNumeric.getSetting().replace("%arg", "price"));
					}
				} else if (cmd.getArg(1).equalsIgnoreCase("remove")) {
					this.removeRank(cmd, cmd.getArg(2), cmd.getArg(3));
				} else if (cmd.getArg(1).equalsIgnoreCase("list")) {
					this.listRanks(cmd, cmd.getArg(2));
				} else if (cmd.getArg(1).equalsIgnoreCase("set")) {
					this.setRank(cmd, cmd.getArg(2), cmd.getArg(3), cmd.getArg(4), cmd.getArg(5));
				} else {
					help(cmd, 0);
				}
			}  else if (cmd.getArg(1).equalsIgnoreCase("info")) {
				this.infoRank(cmd, cmd.getArg(2), cmd.getArg(3));
			} else {
				help(cmd, 0);
			}
		} else {
			help(cmd, 0);
		}
	}

	private void reloadPlugin(IssuedCommand cmd) {
		if (cmd.getSender().hasPermission("paidranks.commands.pr.reload")) {
			this.instance.getServer().getPluginManager().disablePlugin(this.instance);
			this.instance.getServer().getPluginManager().enablePlugin(this.instance);
			super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Reload_Plugin.getSetting());
		} else {
			super.noPerm(cmd);
		}
	}

	private void reloadLanguage(IssuedCommand cmd) {
		if (cmd.getSender().hasPermission("paidranks.commands.pr.reload.language")) {
			this.instance.getDatabaseManager().initLanguage();
			super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Reload_Language.getSetting());
		} else {
			super.noPerm(cmd);
		}
	}

	private void reloadRanks(IssuedCommand cmd) {
		if (cmd.getSender().hasPermission("paidranks.commands.pr.reload.ranks")) {
			this.instance.getDatabaseManager().getRankDatabase().saveDatabase();
			this.instance.getLadderManager().reset();
			this.instance.getDatabaseManager().initRanks();
			super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Reload_Ranks.getSetting());
		} else {
			super.noPerm(cmd);
		}
	}

	private void createLadder(IssuedCommand cmd, String ladder, String arg1, String arg2) {
		if (cmd.getSender().hasPermission("paidranks.commands.pr.ladder.create")) {
			if (!this.manager.hasLadder(ladder)) {
				boolean isDefault = false, isRequiresRank = false;
				if(arg1 != null) {
					isDefault = arg1.equalsIgnoreCase("-default");
					isRequiresRank = arg1.equalsIgnoreCase("-requiresRank");
				}
				if(arg2 != null) {
					if(!isDefault)
						isDefault = arg2.equalsIgnoreCase("-default");
					if(!isRequiresRank)
						isRequiresRank = arg2.equalsIgnoreCase("-requiresRank");
				}
				Ladder ladderx = new Ladder(this.instance, ladder, null);
				ladderx.setRequiresRank(isRequiresRank);
				this.manager.addLadder(ladderx);
				super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Ladder_Create.getSetting() + " " + ((isDefault) && (this.manager.hasDefaultLadder())
						? LanguageSettings.Commands_PaidRanks_Ladder_CreateDefault.getSetting() : ""));
				if (isDefault) {
					this.manager.setDefaultLadder(ladder);
				}
			} else {
				super.error(cmd, LanguageSettings.Commands_LadderExists.getSetting());
			}
		} else {
			super.noPerm(cmd);
		}
	}

	private void removeLadder(IssuedCommand cmd, String ladder) {
		if (cmd.getSender().hasPermission("paidranks.commands.pr.ladder.remove")) {
			if (this.manager.hasLadder(ladder)) {
				Ladder ladderx = this.manager.getLadder(ladder);
				this.manager.removeLadder(ladder);
				if (ladderx.isDefault()) {
					super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Ladder_RemoveDefault.getSetting().replace("%ladder", ladderx.getName()));
				} else {
					super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Ladder_Remove.getSetting().replace("%ladder", ladderx.getName()));
				}
			} else {
				super.error(cmd, LanguageSettings.Commands_LadderDoesNotExist.getSetting());
			}
		} else {
			super.noPerm(cmd);
		}
	}

	private void setLadder(IssuedCommand cmd, String type, String ladder, String value) {
		if (cmd.getSender().hasPermission("paidranks.commands.pr.ladder.set")) {
			if (type.equalsIgnoreCase("default")) {
				if (this.manager.hasLadder(ladder)) {
					this.manager.setDefaultLadder(ladder);
					super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Ladder_DefaultLadder.getSetting().replace("%ladder", this.manager.getDefaultLadder().getName()));
				} else {
					super.error(cmd, LanguageSettings.Commands_LadderDoesNotExist.getSetting());
				}
			}else if(type.equalsIgnoreCase("world")) {
				if (this.manager.hasLadder(ladder)) {
					this.manager.getLadder(ladder).setWorld(value);
					super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Ladder_SetWorld.getSetting());
				} else {
					super.error(cmd, LanguageSettings.Commands_LadderDoesNotExist.getSetting());
				}
			}else{
				super.error(cmd, LanguageSettings.Commands_PaidRanks_Ladder_SetError.getSetting());
			}
		} else {
			super.noPerm(cmd);
		}
	}

	private void listLadders(IssuedCommand cmd) {
		if (cmd.getSender().hasPermission("paidranks.commands.pr.ladder.list")) {
			super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Ladder_List_Top.getSetting());
			String prefix = LanguageSettings.Commands_PaidRanks_Ladder_List_Prefix.getSetting();
			if (!this.manager.getLadders().isEmpty()) {
				if (this.manager.hasDefaultLadder()) {
					super.msg(cmd, prefix + LanguageSettings.Commands_PaidRanks_Ladder_List_Format.getSetting()
							.replace("%name", this.manager.getDefaultLadder().getName()) + " &3Default");
				}
				for (Ladder ladder : this.manager.getLadders()) {
					if (!ladder.isDefault()) {
						super.msg(cmd, prefix + LanguageSettings.Commands_PaidRanks_Ladder_List_Format.getSetting().replace("%name", ladder.getName()));
					}
				}
			} else {
				super.msg(cmd, prefix + LanguageSettings.Commands_PaidRanks_Ladder_NotAvailable.getSetting());
			}
		} else {
			super.noPerm(cmd);
		}
	}
	
	private void toggleRequiresRank(IssuedCommand cmd, String ladder) {
		if (cmd.getSender().hasPermission("paidranks.commands.pr.ladder.toggle.requiresrank")) {
			if(this.manager.hasLadder(ladder)) {
				Ladder ladderx = this.manager.getLadder(ladder);
				ladderx.setRequiresRank(!ladderx.isRequiresRank());
				super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Ladder_Toggle_RequiresRank.getSetting()
						.replace("%ladder", ladderx.getName())
						.replace("%value", ladderx.isRequiresRank() + ""));
			}else{
				super.error(cmd, LanguageSettings.Commands_LadderDoesNotExist.getSetting());
			}
		}else{
			super.noPerm(cmd);
		}
	}
	
	private void infoLadder(IssuedCommand cmd, String ladder) {
		if(cmd.getSender().hasPermission("paidranks.commands.pr.ladder.info")) {
			if(this.manager.hasLadder(ladder)) {
				Ladder ladderx = this.manager.getLadder(ladder);
				super.msgPrefix(cmd, LanguageSettings.Commands_Paidranks_Info_Header.getSetting().replace("%value", "Ladder"));
				super.msg(cmd, LanguageSettings.Commands_Paidranks_Info_Values.getSetting()
						.replace("%name", "Ladder Name")
						.replace("%value", ladderx.getName()));
				super.msg(cmd, LanguageSettings.Commands_Paidranks_Info_Values.getSetting()
						.replace("%name", "Default")
						.replace("%value", ladderx.isDefault() + ""));
				super.msg(cmd, LanguageSettings.Commands_Paidranks_Info_Values.getSetting()
						.replace("%name", "Requires Rank")
						.replace("%value", ladderx.isRequiresRank() + ""));
				super.msg(cmd, LanguageSettings.Commands_Paidranks_Info_Values.getSetting()
						.replace("%name", "Ranks")
						.replace("%value", ""));
				String prefix = LanguageSettings.Commands_Paidranks_Info_Prefix.getSetting();
				if (!ladderx.getRanks().isEmpty()) {
					for (Rank rank : ladderx.getRanks()) {
						super.msg(cmd, prefix + LanguageSettings.Commands_PaidRanks_Rank_List_Format.getSetting()
								.replace("%position", rank.getPosition() + "")
								.replace("%name", rank.getName())
								.replace("%cash", PaidRanksAPI.getFormat(rank.getPrice()))
								.replace("%permission", rank.getPermission()));
					}
				} else {
					super.msg(cmd, prefix + LanguageSettings.Commands_PaidRanks_Rank_NotAvailable.getSetting());
				}
			}else{
				super.error(cmd, LanguageSettings.Commands_LadderDoesNotExist.getSetting());
			}
		}else{
			super.noPerm(cmd);
		}
	}
	
	private void infoRank(IssuedCommand cmd, String ladder, String rank) {
		if(cmd.getSender().hasPermission("paidranks.commands.pr.rank.info")) {
			if(this.manager.hasLadder(ladder)) {
				Ladder ladderx = this.manager.getLadder(ladder);
				if(ladderx.hasRank(rank)) {
					Rank rankx = ladderx.getRank(rank);
					super.msgPrefix(cmd, LanguageSettings.Commands_Paidranks_Info_Header.getSetting().replace("%value", "Rank"));
					super.msg(cmd, LanguageSettings.Commands_Paidranks_Info_Values.getSetting()
							.replace("%name", "Name")
							.replace("%value", rankx.getName()));
					super.msg(cmd, LanguageSettings.Commands_Paidranks_Info_Values.getSetting()
							.replace("%name", "Position")
							.replace("%value", rankx.getPosition() + ""));
					super.msg(cmd, LanguageSettings.Commands_Paidranks_Info_Values.getSetting()
							.replace("%name", "Price")
							.replace("%value", PaidRanksAPI.getFormat(rankx.getPrice())));
					super.msg(cmd, LanguageSettings.Commands_Paidranks_Info_Values.getSetting()
							.replace("%name", "Permission")
							.replace("%value", rankx.getPermission()));
				}else{
					super.error(cmd, LanguageSettings.Commands_RankDoesNotExist.getSetting());
				}
			}else{
				super.error(cmd, LanguageSettings.Commands_LadderDoesNotExist.getSetting());
			}
		}else{
			super.noPerm(cmd);
		}
	}

	private void addRank(IssuedCommand cmd, String ladder, String rank, double price, String permission) {
		if (cmd.getSender().hasPermission("paidranks.commands.pr.rank.add")) {
			if (this.manager.hasLadder(ladder)) {
				Ladder ladderx = this.manager.getLadder(ladder);
				if (!ladderx.hasRank(rank)) {
					ladderx.addRank(rank, permission, price);
					super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Rank_Add.getSetting().replace("%ladder", ladderx.getName()));
				} else {
					super.error(cmd, LanguageSettings.Commands_RankExists.getSetting());
				}
			} else {
				super.error(cmd, LanguageSettings.Commands_LadderDoesNotExist.getSetting());
			}
		} else {
			super.noPerm(cmd);
		}
	}

	private void removeRank(IssuedCommand cmd, String ladder, String rank) {
		if (cmd.getSender().hasPermission("paidranks.commands.pr.rank.remove")) {
			if (this.manager.hasLadder(ladder)) {
				Ladder ladderx = this.manager.getLadder(ladder);
				if (ladderx.hasRank(rank)) {
					ladderx.removeRank(rank);
					super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Rank_Remove.getSetting().replace("%ladder", ladderx.getName()));
				} else {
					super.error(cmd, LanguageSettings.Commands_RankDoesNotExist.getSetting());
				}
			} else {
				super.error(cmd, LanguageSettings.Commands_LadderDoesNotExist.getSetting());
			}
		} else {
			super.noPerm(cmd);
		}
	}
	
	private void setRank(IssuedCommand cmd, String ladder, String rank, String type, String value) {
		if(cmd.getSender().hasPermission("paidranks.commands.pr.rank.set")) {
			if(this.manager.hasLadder(ladder)) {
				Ladder ladderx = this.manager.getLadder(ladder);
				if(ladderx.hasRank(rank)) {
					Rank rankx = ladderx.getRank(rank);
					if(type.equalsIgnoreCase("cost") || type.equalsIgnoreCase("price")) {
						if(cmd.isNumeric(5)) {
							rankx.setPrice(cmd.getDouble(5));
							this.instance.getDatabaseManager().getRankDatabase().saveDatabase();
							super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Rank_Set.getSetting()
									.replace("%type", "price")
									.replace("%value", value)
									.replace("%rank", rankx.getName()));
						}else{
							super.error(cmd, LanguageSettings.Commands_MustBeNumeric.getSetting().replace("%arg", "type"));
						}
					}else if(type.equalsIgnoreCase("perm") || type.equalsIgnoreCase("permission")) {
						rankx.setPermission(value);
						this.instance.getDatabaseManager().getRankDatabase().saveDatabase();
						super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Rank_Set.getSetting()
								.replace("%type", "permission")
								.replace("%value", value)
								.replace("%rank", rankx.getName()));
					}else if(type.equalsIgnoreCase("id") || type.equalsIgnoreCase("position") || type.equalsIgnoreCase("pos")) {
						if(cmd.isNumeric(5)) {
							int id = cmd.getInteger(5);
							if ((id > 0) && (id <= ladderx.getRanks().size())) {
								ladderx.setPosition(rankx.getPosition(), id);
								this.instance.getDatabaseManager().getRankDatabase().saveDatabase();
								super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Rank_Set.getSetting()
										.replace("%type", "position")
										.replace("%value", value)
										.replace("%rank", rankx.getName()));
							} else {
								super.error(cmd, LanguageSettings.Commands_PaidRanks_Rank_SetPositionError.getSetting().replace("%id", ladderx.getRanks().size() + ""));
							}
						}else{
							super.error(cmd, LanguageSettings.Commands_MustBeNumeric.getSetting().replace("%arg", "position"));
						}
					}else{
						super.error(cmd, LanguageSettings.Commands_PaidRanks_Rank_SetError.getSetting());
					}
				}else{
					super.error(cmd, LanguageSettings.Commands_RankDoesNotExist.getSetting());
				}
			}else{
				super.error(cmd, LanguageSettings.Commands_NoPermission.getSetting());
			}
		}else{
			super.noPerm(cmd);
		}
	}

	private void listRanks(IssuedCommand cmd, String ladder) {
		if (cmd.getSender().hasPermission("paidranks.commands.pr.rank.list")) {
			if (this.manager.hasLadder(ladder)) {
				Ladder ladderx = this.manager.getLadder(ladder);
				super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Rank_List_Top.getSetting().replace("%ladder", ladderx.getName()));
				String prefix = LanguageSettings.Commands_PaidRanks_Rank_List_Prefix.getSetting();
				if (!ladderx.getRanks().isEmpty()) {
					for (Rank rank : ladderx.getRanks()) {
						super.msg(cmd, prefix+ LanguageSettings.Commands_PaidRanks_Rank_List_Format.getSetting()
								.replace("%position", rank.getPosition() + "")
								.replace("%name", rank.getName())
								.replace("%cash", PaidRanksAPI.getFormat(rank.getPrice()))
								.replace("%permission", rank.getPermission()));
					}
				} else {
					super.msg(cmd, prefix + LanguageSettings.Commands_PaidRanks_Rank_NotAvailable.getSetting());
				}
			} else {
				super.error(cmd, LanguageSettings.Commands_LadderDoesNotExist.getSetting());
			}
		} else {
			super.noPerm(cmd);
		}
	}

	private void setupHelp() {
		this.list.addOption(new Option("/pr - This screen.", "paidranks.commands.pr.help"));
		this.list.addOption(new Option("/pr help [page]- Page of help.", "paidranks.commands.pr.help"));
		this.list.addOption(new Option("/pr reload - Reloads the whole plugin.", "paidranks.commands.pr.reload"));
		this.list.addOption(new Option("/pr reload language - Reloads the language file.", "paidranks.commands.pr.reload.language"));
		this.list.addOption(new Option("/pr reload ranks - Reloads the ranks file.", "paidranks.commands.pr.reload.ranks"));
		this.list.addOption(new Option("/pr ladder create <ladderName> [-default and|or -requiresRank] - Creates a ladder.", "paidranks.commands.pr.ladder.create"));
		this.list.addOption(new Option("/pr ladder remove <ladderName> - Removes a ladder.", "paidranks.commands.pr.ladder.remove"));
		this.list.addOption(new Option("/pr ladder set <default | world> <ladderName> [value] - Sets the 'value' to the type.", "paidranks.commands.pr.ladder.set"));
		this.list.addOption(new Option("/pr ladder toggle requiresRank <ladderName> - Toggles whether the ladder requires a rank.", "paidranks.commands.pr.ladder.toggle.requiresrank"));
		this.list.addOption(new Option("/pr ladder info <ladderName> - Get information about the ladder.", "paidranks.commands.pr.ladder.info"));
		this.list.addOption(new Option("/pr ladder list - Lists all the available ladders.", "paidranks.commands.pr.ladder.list"));
		this.list.addOption(new Option("/pr rank add <ladderName> <rankName> [price] [permission] - Add a rank to a ladder.", "paidranks.commands.pr.rank.add"));
		this.list.addOption(new Option("/pr rank remove <ladderName> <rankName> - Removes a rank from a ladder", "paidranks.commands.pr.rank.remove"));
		this.list.addOption(new Option("/pr rank set <ladderName> <rankName> <price | perm | position> <value> - Sets the 'value' to the type.", "paidranks.commands.pr.rank.set"));
		this.list.addOption(new Option("/pr rank info <ladderName> <rankName> - Gets information about the rank.", "paidranks.commands.pr.rank.info"));
		this.list.addOption(new Option("/pr rank list <ladderName> - Lists all the ranks within that ladder.", "paidranks.commands.pr.rank.list"));
	}

	private void help(IssuedCommand cmd, int page) {
		if (cmd.getSender().hasPermission("paidranks.commands.pr.help")) {
			page = this.list.checkPage(cmd.getSender(), page);
			super.msgPrefix(cmd, LanguageSettings.Commands_PaidRanks_Help_Top.getSetting().replace("%page", (page + 1) + "")
					.replace("%totalpages", this.list.getTotalPages(cmd.getSender()) + ""));
			super.msg(cmd, LanguageSettings.Commands_PaidRanks_Help_Disclaimer.getSetting());
			String dud = ChatColor.DARK_GRAY + "    - ";
			for (String m : this.list.getOptions(cmd.getSender(), page)) {
				m = m.replace("/", ChatColor.DARK_GREEN + "/").replace("-", ChatColor.AQUA + "-" + ChatColor.GREEN);
				int index = m.indexOf(' ');
				if (index < m.length()) {
					String prefix = m.substring(0, index) + ChatColor.GREEN;
					super.msg(cmd, this.instance.getColorized(dud + prefix + m.substring(index)));
				} else {
					super.msg(cmd, this.instance.getColorized(dud + m));
				}
			}
		}
	}
}
