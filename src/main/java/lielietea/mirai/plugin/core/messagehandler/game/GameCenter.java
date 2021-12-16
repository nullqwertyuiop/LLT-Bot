package lielietea.mirai.plugin.core.messagehandler.game;

import lielietea.mirai.plugin.core.messagehandler.game.bancodeespana.SenoritaCounter;
import lielietea.mirai.plugin.core.messagehandler.game.fish.Fishing;
import lielietea.mirai.plugin.core.messagehandler.game.jetpack.JetPack;
import lielietea.mirai.plugin.core.messagehandler.game.mahjongriddle.MahjongRiddle;
import lielietea.mirai.plugin.core.messagehandler.game.montecarlo.CasinoCroupier;
import lielietea.mirai.plugin.core.messagehandler.responder.autoreply.FurryGamesIndex;
import lielietea.mirai.plugin.core.messagehandler.responder.autoreply.Nudge;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import java.io.File;

public class GameCenter {

    public static void handle(MessageEvent event){

        if(event instanceof GroupMessageEvent){
            MahjongRiddle.riddleStart((GroupMessageEvent) event);
            Nudge.mentionNudge((GroupMessageEvent) event);
        }

        JetPack.start(event);
        Fishing.go(event);
        SenoritaCounter.go(event);
        CasinoCroupier.handle(event);
        FurryGamesIndex.search(event);
        //Foodie.send(event);

    }

}
