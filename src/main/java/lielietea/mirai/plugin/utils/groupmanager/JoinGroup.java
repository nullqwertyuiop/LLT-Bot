package lielietea.mirai.plugin.utils.groupmanager;

import lielietea.mirai.plugin.utils.idchecker.GroupID;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JoinGroup {
    
    static Map<Long, Boolean> joinGroupEventFlag = new HashMap<>();
    public static void sendNotice(BotJoinGroupEvent event) throws InterruptedException {
        if (!joinGroupEventFlag.containsKey(event.getGroupId())){
            joinGroupEventFlag.put(event.getGroupId(), false);
        }
        if (!joinGroupEventFlag.get(event.getGroupId())){
            joinGroupEventFlag.put(event.getGroupId(), true);
            event.getGroup().sendMessage(Speech.joinGroup);
            Thread.sleep(2000);
            event.getGroup().sendMessage(Speech.disclaimer);
            Thread.sleep(2000);
            event.getGroup().sendMessage(Speech.help);
            String joinMessage = "七筒已加入"+String.valueOf(event.getGroupId())+"-"+event.getGroup().getName()+"。";
            Objects.requireNonNull(event.getBot().getGroup(GroupID.DEV)).sendMessage(joinMessage);
            event.getGroup().getOwner().sendMessage("您好，七筒已经加入了您的群"+event.getGroup().getName()+" - "+event.getGroup().getId()+"，请在群聊中输入/help 以获取相关信息。如果七筒过于干扰群内秩序，请将七筒从您的群中移除。");
        }
        else{
            joinGroupEventFlag.remove(event.getGroupId());
        }
    }
}
