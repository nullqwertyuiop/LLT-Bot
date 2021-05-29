package lielietea.mirai.plugin.dice;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 以下为骰子操作方法演示
 * <p>{@code
 * List<Integer> reult = New Dice().setBound(10)
 *                       .setRepeat(10)
 *                       .roll()
 *                       .toList();\
 * }</p>
 */
public class Dice {
    int bound;
    int repeat;
    List<Integer> result;

    public Dice() {
        this.bound = 6;
        this.repeat = 1;
        result = new ArrayList<>();
    }

    public Dice(int bound) {
        this.bound = bound;
        this.repeat = 1;
        result = new ArrayList<>();
    }

    public Dice(int bound,int repeat) {
        this.bound = bound;
        this.repeat = repeat;
        result = new ArrayList<>();
    }

    /**
     * 设置投掷结果上限
     * @param bound 骰子最大骰出数
     * @return 该骰子本身
     */
    Dice setBound(int bound){
        this.bound = bound;
        return this;
    }

    /**
     * 设置投掷次数
     * @param repeat 投掷次数
     * @return 该骰子本身
     */
    Dice setRepeat(int repeat){
        this.repeat = repeat;
        return this;
    }

    /**
     * 扔骰子
     * <p>在进行最终操作前，必须扔骰子</p>
     * @return 该骰子本身
     */
    Dice roll(){
        if(!result.isEmpty()) result = new ArrayList<>();
        for(int i=0;i<repeat;i++){
            result.add(new Random(System.nanoTime()).nextInt(bound)+1);
        }
        return this;
    }

    /**
     * 返回一个结果组成的List
     * <p>这是一个最终操作</p>
     * @return 一个包含了投掷结果的ArrayList
     */
    List<Integer> toList(){
        return result;
    }

    /**
     * 私聊某人投掷结果
     * <p>这是一个最终操作</p>
     * @param directMessageTarget 私聊对象
     */
    void privatelyInfoResult(Friend directMessageTarget){
        directMessageTarget.sendMessage(buildMessage());
    }

    /**
     * 在某群中广播投掷结果并引用投掷命令
     * <p>这是一个最终操作</p>
     * @param quoteMessage 被引用的消息
     * @param broadcastGroup 广播对象
     */
    void broadcastResultByQuote(MessageChain quoteMessage,Group broadcastGroup){
        broadcastGroup.sendMessage(buildMessage(quoteMessage));
    }

    /**
     * 在某群中广播投掷结果
     * <p>这是一个最终操作</p>
     * @param broadcastGroup 广播对象
     */
    void broadcastResult(Group broadcastGroup){
        broadcastGroup.sendMessage(buildMessage());
    }

    /**
     * 检查某语句是否是一个投掷骰子的命令
     * @param input 被检查的语句
     * @return 检查结果
     */
    public static boolean check(String input){
        String pattern = "^((\\dice)|(\\d))[ ]?[0-9]{1,8}";
        return Pattern.matches(pattern,input);
    }

    MessageChain buildMessage(){
        MessageChainBuilder message = new MessageChainBuilder();
        message.append("您掷出的点数是:");
        result.stream().forEach(result->{
            message.append("您掷出的点数是"+result+".");
        });
        return message.build();
    }

    MessageChain buildMessage(MessageChain quoteMessage){
        MessageChainBuilder message = new MessageChainBuilder();
        message.append("您掷出的点数是:");
        message.append(new QuoteReply(quoteMessage));
        result.stream().forEach(result->{
            message.append(result+".");
        });
        return message.build();
    }



}
