package lielietea.mirai.plugin.utils;


import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GroupPolice {

    static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    final static GroupPolice INSTANCE = new GroupPolice();

    GroupPolice() {
        executor.scheduleAtFixedRate(new AutoClear(), (long)0.5, 3, TimeUnit.HOURS);
    }

    static public GroupPolice getINSTANCE() {
        return INSTANCE;
    }

    static class AutoClear implements Runnable {
        @Override
        public void run() {
            for (Bot bot : Bot.getInstances()) {
                for (Group group : bot.getGroups()) {
                    if (IdentityUtil.DevGroup.DEFAULT.isDevGroup(group.getId())) continue;
                    if (group.getMembers().getSize() <= 10) {
                        group.sendMessage("七筒目前不接受加入10人以下的群聊，将自动退群，请在其他群中使用七筒。感谢您使用七筒的服务。");
                        MessageUtil.notifyDevGroup("由于群聊人数不满10人，七筒已经从"+group.getName()+"("+group.getId()+")中离开。");
                        executor.scheduleWithFixedDelay(group::quit, 0, 15, TimeUnit.SECONDS);
                    }

                    for (NormalMember nm : group.getMembers()) {
                        for (Bot bot2 : Bot.getInstances()) {
                            if (bot2.getId() == (bot.getId())) continue;
                            if (nm.getId() == bot2.getId()) {
                                group.sendMessage("检测到其他在线七筒账户在此群聊中，将自动退群。");
                                MessageUtil.notifyDevGroup("由于检测到其他七筒，七筒已经从"+group.getName()+"("+group.getId()+")中离开。");
                                executor.scheduleWithFixedDelay(group::quit, 0, 15, TimeUnit.SECONDS);
                            }
                        }
                    }
                }
            }
        }
    }

    public void ini(){}
}
