package me.Oxygen.modules.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.Oxygen.Oxygen;
import me.Oxygen.event.Event;
import me.Oxygen.event.EventTarget;
import me.Oxygen.event.events.EventChat;
import me.Oxygen.event.events.EventPacket;
import me.Oxygen.injection.interfaces.IS08PacketPlayerPosLook;
import me.Oxygen.modules.Category;
import me.Oxygen.modules.Module;
import me.Oxygen.modules.ModuleRegister;
import me.Oxygen.modules.combat.KillAura;
import me.Oxygen.ui.ClientNotification.Type;
import me.Oxygen.utils.ClientUtil;
import me.Oxygen.utils.timer.TimeHelper;
import me.Oxygen.value.Value;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S45PacketTitle;

@ModuleRegister(name = "Collective", category = Category.PLAYER, enabled = true)
public class Collective extends Module{
	
	String intcihui[] = { "我现在在这埋汰你呢小老弟", "你个没速度的小废物", "我好象你爸爸似的你难道自己不清楚现在的情况吗", "然后你完全没有力量你明白你的扣字垃圾吗小废物",
			"我好象你爸爸似的你是不了解情况怎么的呢", "你为什么在这里耀武扬威的", "现在了解什么情况你完全垃圾似的知道了吗", "你好象垃圾似的你怎么和我对抗呢", "嘻嘻嘻嘻你能出来告诉我吗",
			"你完全没有速度你明白什么情况吗垃圾似的出来找揍", "你自己好象我儿子似的只能污蔑我还是想怎么样吗", "我就是你父亲似的随便殴打你一个完全没什么力量的儿子", "父亲我随便殴打你这样的事实情况了啊儿子",
			"我现在不是随便殴打你吗自闭青年", "你自己现在不了解情况还是怎么的", "自闭症儿童开始殴打你登峰造极的爸爸了啊", "我怎么感觉你和我的自闭儿子似的反抗爸爸",
			"儿子你现在自己怎么反抗的爸爸的你知不知道什么情况啊", "现在的你看见你爹的各种速度害怕了还是怎么的", "爸爸随意的打的你了啊你清楚事实吗", "你还想无力地反抗我还是怎么啊弟弟",
			"你自己不了解情况还是怎么的你完全垃圾似的", "嘻嘻嘻你的b脸呢", "你妈给你b脸你装你妈b里去了呢", "你好像完完全全不清楚情况", "你没有发现你根本不是爹爹的对手吗", "爹爹我随随便便干翻你母亲",
			"你个狗儿子只有畏畏缩缩的滚了", "你现在是不是坐在电脑前手心出汗呢", "你是不是紧张了呢你是不是语无伦次了呢", "你现在能做的只有乱敲键盘满怀恐惧地向我暗示你内心的恐惧呢",
			"你没有发现现在就像一个跳梁小丑一样取悦我吗", "我随随便便殴打你殴打你个自闭东西", "为什么为什么动不动操你妈傻逼", "这是你翻来覆去的词汇呢", "小朋友拿出你的实力来可好",
			"你没有发现你根本不是爹爹的对手吗", "你是不是死皮赖脸的给我上啊", "死了妈的小伙子啊", "你别在无济于事的给你爸爸我死皮赖脸的上了", "你就是死皮赖脸的小伙子是不是这样你快回答我",
			"你是个啥啊你个死了妈的小伙子你就是个小废物啊", "小伙子你八仙过海的赶来狐假虎威地狗你爹爹我呢", "你是不是垃圾狗篮子啊你告诉大家你是不是啊", "你是不是大言不惭地吹嘘啊垃圾狗篮子呢",
			"你怎么了啊是不是畏惧你爸爸我了啊", "你个狗篮子是不是啊小伙子你妈死了", "你承不承认你是个耀武扬威半身不遂的垃圾狗篮子啊", "你赶紧的跟上啊", "是不是没有办法提高你的速度了",
			"自己是不是已经感觉无能为力了啊", "是不是没有速度焦头烂额了啊", "你是不是知道你自己要输给你登峰造极的爸爸我了啊", "你是不是没有自知之明了啊",
			"你觉得你要输给你登峰造极的爸爸我啊你开始不是风风火火的吗", "你干什么啊墨迹什么啊你什么自闭症速度啊", "你难道不应该用强大到令人退缩的词汇来攻击我吗", "弟弟你怎么词穷了啊",
			"窝囊废东西是不是啊小伙子", "你还大言不惭地吹嘘你的速度是不是啊", "你有什么资本吹嘘", "你无人问津的速度啊你一五一十的告诉你爸爸我啊", "你是不是悬心吊胆的以为你可以赢过你登峰造极的爸爸我啊",
			"我看你沾沾自喜的样子你是不是以为你可以胜利啊", "小伙子你是不是窝囊废弟弟啊", "你是不是家喻户晓的耀武扬威的不可一世的窝囊废啊", "你怎么成了一个瓮中之鳖啊", "你是不是疯疯癫癫无法自拔了啊",
			"窝囊废东西是不是妄自尊大啊", "你为什么怎么用你哩哩啦啦的言语攻击你登峰造极的爸爸我啊", "你是不是想用你婆婆妈妈的词汇击败你老老实实的爹爹我啊", "你是不是开始沾沾自喜了啊",
			"你是不是以为你可以击败登峰造极的爸爸我啊", "你现在是不是觉得莫名其妙的啊", "为什么你爹打字速度这么快啊", "你是不是畏惧你爹爹的速度了啊", "你是不是对这些词汇一窍不通啊", "你是不是什么都不懂啊",
			"你怎么唯唯诺诺的了啊", "你是不是畏惧我了啊", "窝囊废东西你是不是苟延残喘了啊", "你是不是汗流浃背了啊是不是气喘吁吁了啊", "垃圾狗篮子跟上你爹爹我的节奏啊好不好啊", "快用你三三两两的词汇攻击我",
			"你一个井底之蛙怎么可能知道天比你个子高地比你脸皮厚嘛", "老老实实的当你的窝囊废去啊", "你干什么啊三番五次的攻击你爸爸我啊", "你怎么还纹丝不动啊", "你是又聋又瞎的吗", "你是不明不白的吗",
			"我叫你去老老实实的当窝囊废啊你迷迷糊糊的吗", "你是不是听不懂你爹爹说的话啊", "你爹爹我叫你去当窝囊废啊", "你干什么啊是不是开始恼羞成怒了啊", "怎么还不去老老实实地当你的窝囊废啊",
			"怎么还在我面前耀武扬威的啊", "你是不是想继续用你那断断续续的言语证明你的窝囊废啊", "你再怎么样都是无济于事的小老弟你了解不了解啊", "小老弟你就是个独一无二的窝囊废啊", "你是不是不可一世的窝囊废啊",
			"你为什么唧唧喳喳的啊", "快用你断断续续的言语攻击我啊", "你要是没有能力攻击我你就别在这婆婆妈妈的好不好啊崽种东西", "你是不是窝囊废东西啊你告诉我啊", "你用你稀稀拉拉支离破碎的言语攻击我啊",
			"你怎么三番五次地污蔑你爹爹我啊", "你是不是以为你有那一点零零散散的言语就可以击败我啊", "你小心你爹爹我大义灭亲啊", "你赶紧滚啊小老弟别提心吊胆的啊", "爹爹我不会吃了你的啊",
			"你干什么啊还纹丝不动是不是啊", "赶紧从地球上消失啊", "为什么还唧唧歪歪哩哩啦啦叽叽喳喳叭叭叭叭的啊", "你是不是不想滚啊", "你怎么莫名其妙地就不滚了啊", "你是不是开始肆无忌惮了啊",
			"你爹爹我见识多了别在狗狗妈妈了好吗", "你好象垃圾似的你有什么脾气你告诉大家知道了吗", "你认为就你这点词汇能把我打倒在这小小的网络世界中吗", "你就是幻想反抗你爸爸我还是怎么啊弟弟似的",
			"你自己清楚明白你根本就是垃圾似的怎么的呢", "我残酷殴打你这样的事实情况你好象不清楚", "残酷地殴打你这样的恶霸事实你自己告诉我呢", "为什么没有速度你告诉你爸爸我", "为什么窝囊废你告诉你爸爸我啊",
			"窝囊废东西你还不滚啊", "你恶心了你爸爸我你明不明白啊", "你自己好象没有水平但是不了解情况怎么的呢", "我现在和你随便开始你怎么不承认你自己垃圾东西", "我觉得我就是你爸爸还是怎么啊出来告诉我似的",
			"你自己好象不清楚了解情况还是怎么的告诉大家埃", "但是你这样的垃圾速度清楚了解情况还是怎么的殴打我埃", "我随便的殴打你这样的垃圾似的好象们有反抗把", "我觉得你完全没有实力反抗爹的速度你怎么不知道啊",
			"我觉得你就是个废品你自己好像没有文化了解吗", "我觉得你是废品你怎么反抗我的速度呢孩子", "你了解吗你知道吗你清楚吗明白吗晓得吗", "你真以为就凭你那毫无逻辑残缺不齐的词能带给你所谓的优越感和胜利感吗",
			"你的词汇就是艹B艹妈这样下流的吗", "你不就是个张口闭口艹B艹妈满脑子想着艹B艹妈的自闭玩意吗", "你为什么那么语无伦次那么没有逻辑", "你就是完全没什么文化水平似的垃圾废物弟弟",
			"我随便殴打你这样的事实儿子你的言语行动都装作不清楚似的", "你这个垃圾速度和我继续的对抗呢对吗", "你真的以为你能称霸这键盘界吗", "你完全垃圾似的没有水平怎么反抗我", "你告诉在场的各位啊",
			"我随便殴打你的事实不能进行唠嗑怎么的告诉我呢啊", "我不认为你这个垃圾似的能够开始唠嗑怎么的", "马上出来告诉大家可以了吗你完全垃圾似的知道吗", "我就是你爸爸这样的事实你自己好象不清楚",
			"我随便殴打你这样的事实情况你自己告诉我还是怎么", "我好象你爸爸似的小老弟你是不清楚事实还是怎么的怎么就没有点速度呢", "你知道吗殴打你这个弱智垃圾完全不需要什么水平",
			"你自己好象没有水平似不了解情况怎么的", "你严重的没有水平似的你晓得啊", "你自己没有速度开始逃避我?你是怎么的啊", "你就是不能反抗你父亲我还是怎么的啊速度滚出来告诉我啊",
			"我不明白什么了啊我觉得好象我是你爸爸了呢", "火葬场打电话问你妈要几分熟啊你回答啊", "马上狗肉节了你为什么不给你家里人安顿好避难所还出来打游戏啊你回答啊", "你是根本没长脑子还是脑子发霉啊你回答啊",
			"你赶紧回到你妈生殖器里重新改造重新做狗啊", "你这个畏畏缩缩的小老弟赶紧滚啊", "你话那么多怎么不坐你妈坟头跟她慢慢唠嗑", "你爹爹我反手赏你亲亲妈妈一个不侧漏的骨灰盒",
			"我就是养条狗在键盘上放个屁的速度都比你手速快", "别喷粪好吗再喷你脑子就空了你知道吗", "爸爸一把剔骨刀插你妈脑门上一脚踹过去给你妈头盖骨撬开现在你狗娘脑浆子都蹦上九霄云外了你知道吗",
			"我用jb把你狗娘脑袋抽傻了现在你妈逢人就吹嘘她被男性生殖器干成了脑震荡你清楚吗", "大爹徒手撕开你狗娘的大屁眼子再找来你亲爹把他脑袋塞进去让你妈的屎橛子替你爹补充一下生物能量你明白吗",
			"不要那种威胁的语气跟我对话行不行啊", "你以为你骂几句傻逼就无人能匹敌了吗", "你不要那么风趣幽默好吗", "你有没有发现你bb的都是一堆废话", "我完全可以无视你那垃圾语言",
			"我估计你无法抵挡我这洪水般的词汇", "你已经无力回天", "你只能乱敲键盘", "你告诉我你内心在哭泣而且无力回天是不是的", "你怎么不好好考虑一下你到底有没有那个实力跟我抗衡",
			"跟个残废人墨迹半天有多无聊你知道吗", "别再用你那三言两语支离破碎的词语来攻击我好吗", "你不觉得你这稀稀拉拉的词汇太没杀伤力了吗", "就你这窝囊废样子你还想揍倒我吗", "你现在痴人说梦异想天开了吧",
			"你怎么到现在都不理解我揍你的意图呢", "你太废物为什么为什么我总有忍不住揍你的冲动", "你现在是不是在思索着该怎么回答我呢", "你一五一十的告诉我行不", "你是不是被我打击的无法言语了呢",
			"是不是在失望的深渊里面痛苦的无法自拔", "你认为你就这么跟我说几句话旧能跟你大哥我抗衡了吗", "我都不想再打击你这个没有力气的弟弟了", "跟你这么说简直就是在侮辱我的曼巴狂蛇键盘",
			"你以为就你这点词汇能把我打倒在这虚拟的网络世界中吗", "你以为你能称霸这键盘界吗", "你是不是就连打句我草你妈你都没有勇气按下回车吗", "还在那摇摆不定失去重心是吗",
			"双眼涣散的你看到了你爹爹我对你洪水般的攻击吗", "你那鬼哭狼嚎的语言在我眼里是多么地不堪一击", "大哥我随意践踏你的自尊和人格", "你还能用你那零零碎碎的语言反击我么",
			"你说话好吗我求你了提升一下你的蛤蟆速度啊", "你还要继续发扬你那厚颜无耻的精神吗", "你回去抱着你妈一起痛哭好吗", "只有你妈能给你安慰了行吗", "你看看你那有气无力的挣扎",
			"你是不是要跪下求我停止攻击了呢", "看好你爹爹是如何用华丽的词汇来教育你的啊", "你感到前所未有的恐惧了吗", "你的心是否正在因为畏惧你爹爹的速度而没命地跳动", "弟弟你是畏惧我了吗",
			"你还不快滚蛋吗", "你可以继续用你那支离破碎的语言和不要脸的功夫继续和我对话", "可是你觉得你还有什么呢", "你就是一个废物你懂吗", "你那毫无逻辑残缺不齐的词真的能带给你快乐和胜利感吗",
			"你是不是想气急败坏的冲到现实来找我呢", "用你那残缺发抖的双手来揍我吗", "你只能撕破你第n层脸皮来求我绕了你", "继续找借口来逃避我吧", "你现在内心哭泣了是吗", "对你对话使我感到了一种莫名的耻辱",
			"我不想再和残废人墨迹了你懂吗", "你为什么连最基本的词汇都忘的一干二净了呢", "你是在紧张吗", "你空白的大脑偶尔也思考这复杂的局势", "你还想着凭借你那点词汇赢过我吗", "你现在是什么处境呢",
			"你是不是希望让你爹爹手下留情呢", "你是不是在想要怎么回答我", "你为什么哑巴了呢", "你为什么不敢打字了呢", "你为什么一跟我说话就开始结结巴巴反反复复的呢", "你为什么还要垂死挣扎呢",
			"你是不是跟我说一句话要经过大脑半天的思考才敢发出来", "你怎么窝囊废到前无古人后无来者的地步了", "你真的无药可救了你知道吗", "你为什么要在我面前红红火火班门弄斧的",
			"你为什么要当着我的面唧唧歪歪结结巴巴的", "你就没有一颗羞耻之心吗", "你那平平淡淡的语言难以抵挡我", "我真的想给你一次毁灭性的打击", "你的无知只是我脚下的一堆凌土你知道不",
			"你记住以后不要再用你那粗俗的语言来攻击我就像让你爹爹我来定格你的人品", "你不要见到我就一副闻风丧胆的样子", "你蹲厕所去看看你那窝囊废形象", "我真的不知道要能拿什么词语来形容你这个弟弟",
			"你在你大哥我面前是永远抬不起头的你知道不知道啊弟弟", "你那点词汇那么的不堪一击", "你还想用你那不堪的语言来激起我的怒火", "我简直不屑与你废话你清楚明白吗", "你以后不要在我面前装了好吗",
			"没有一点点实力还在我面前耀武扬威的你不觉得丢脸吗", "你何来的自信跟我抗衡啊。", "你那些流离失所的语言早就已经被我攻击的支离破碎", "你还要我挥洒着这些语言来跟你描述我淋漓尽致的语言么",
			"你自己告诉你大哥我你那弱小的身躯能承受住我那犀利的语言么", "什么什么什么你这个垃圾", "怎么怎么怎么你这个废物", "为什么为什么为什么你要用那么低贱而庸俗的语言来跟我抗衡呢你告诉我呢",
			"你这个乱七八糟的玩意你告诉我好不好", "你就这么束手待毙颠三倒四的等着我来收拾你是吗", "我平平常常的扣字速度轻轻松松就能把你攻击得一塌糊涂", "你还能怎么样", "你难道还想继续在我这展示自己有多丢脸吗",
			"丢脸好玩吗好意思吗", "死了妈的你到底行不行啊", "我的语言对你来说已经无关紧要", "因为你早就被我揍的全身麻木了是不", "你从开始就根本不敢回击我是不是的", "你一开始的反击只是你的本能是不是的",
			"要不要让我停止你的苟延残喘", "在键盘上无奈地敲出“我错了”好吗", "你那点零零星星的词汇", "你的希望是不是就来自于你那偶然的灵光一现", "你有什么资格再来跟我对话", "我不是什么所谓的大手",
			"但是我在你眼里永远是神圣而不可侵犯的你知道吗", "看着你那可悲可笑的滑稽样我心底产生了一种悲痛感", "你到底是怎么长大的", "我真想去问候一下你妈", "到底是怎么把你这酒囊饭袋生出来的",
			"登峰造极出类拔萃无人能敌的世纪窝囊废", "你回去再修炼几十年再跟我来对抗吧", "现在你没有这个能力", "你不知道爸爸我的速度可以完全吧你抹杀了啊你怎么和我相提并论了阿",
			"你气急败坏的窝囊废怎么和我絮叨了啊你怎么按么恬不知耻了啊", "你松松垮垮的词汇怎么和我匹敌的阿", "你不知道自己恨窝囊废阿", "你现在的词汇怎么那么窝囊废了啊", "小老弟你怎么回事儿啊",
			"你气急败坏的和我絮叨你那不要脸的词了阿", "还厚颜无耻的跑到我面前吹嘘你的实力是多么的强悍", "你难道就真的不知道羞耻二字怎写吗", "拿着几年前百度上已经开始传的沸沸扬扬的泛滥词汇",
			"在你这段七拼八凑的泛滥词汇里我怎么就丝毫看不出其中的含义", "我现在用登峰造极的完全可以抹杀你的自尊心了你难道还不知道阿", "你现在的速度可以说的狗急跳墙的速度了你还怎么和我媲美的阿",
			"为什么你老是用你那三三两两的杀伤力来挑战我登峰造极的水平", "你以为你狗刨式的打字方法就能够击败我出神入化的水平", "不要以为你是东方不败", "其实你就是酒囊饭袋",
			"你只有你那蜗牛般的速度和你那唧唧歪歪的词汇", "你告诉我你为什么在这里耀武扬威", "你认为你那恶心的词汇可以伤到你爸爸我", "你是不是就这点本事", "为什么没有速度你告诉你爸爸我",
			"为什么窝囊废你告诉你爸爸我啊", "窝囊废东西你还不滚", "你恶心了你爸爸我", "你为什么在这里耀武扬威", "为什么在这里唧唧喳喳", "你为什么为什么语无伦次", "你告诉你爸爸我告诉我为什么",
			"你这样苟活于世", "是不是给你祖上添光彩了呢", "你看看你那可怜的我装什么了", "您倒是告诉我呢", "我装什么都比你装b好", "你是不是不知道你有多难堪呢",
			"是不是要你妈妈我把你打的一败涂地你才知道你地下有多厚呢", "现在窝囊废我告诉你", "我替你感到悲哀你知道么", "你气急败坏的说你要灭我", "你怎么灭啊小老弟", "滚回你妈妈子宫去深造几年再来和我抗衡",
			"你耀武扬威做什么阿", "你横行霸道干什么阿", "你这窝囊东西", "你这瘠薄东西", "你这废物东西", "我见识多了", "别在爹爹妈妈了好吗", "以后见了我十米开外就要给我跪下知道没有",
			"别老用你那窝囊废", "耀武扬威这个字眼浑浑噩噩活一辈子", "就你这个样子的太给中国十几亿人口丢脸了", "你的语言不彳亍你需要学习需要教育", "我真的真的为你妈妈感觉到非常地伤心",
			"你不要总是和我耀武扬威的好不好啊", "你也不要在用那杂乱无章的语言和我说话好不好啊", "你告诉告诉我你到底怎么了", "你有牌面吗当然没有", "你拿什么跟我玩哦", "你拿什么跟我玩呢",
			"你没有能力跟上爹的飞一般的速度", "你告诉我你妈妈怎么把你带到地球上的呢", "你告诉我你爸爸怎么了啊", "你告诉我你妈妈怎么了啊", "你妈妈死了你知不知道", "你爸爸也死了你知不知道",
			"你爸爸妈妈死了你还有心情上网", "你为什么这么不孝", "你告诉告诉我可以嘛", "你为什么不告诉我呢", "你说话可以嘛", "你是害怕了吗", "你是退缩了吗", "你为什么不拿出你的词语驳回我的理论呢",
			"你可怕了是嘛为什么呢", "我小手哦你为什么怕我呢", "你为什么不说话呢", "孩子别装逼了还是嘛", "在跟我装b我把你妈比撕烂了塞你嘴里哦", "你是不是在哭泣哦", "我知道你哭了",
			"你现在都泪流满面了是嘛", "你告诉告诉我", "你是公是母是雌是雄呢", "我是你亲爹么你告诉我", "爹知道你激动了", "你为什么激动呢", "你是紧张了么", "你是遇见了爸爸我紧张了么",
			"你为什么会遇见爸爸我呢", "因为爹是打狗专家呢", "爹为什么要打狗呢", "因为你是狗呢", "你为什么是狗呢", "因为你妈妈没把你教好呢", "为什么没把你教好呢", "因为你妈妈忙着卖B了",
			"你妈妈为什么忙着卖B呢", "因为要挣钱养活你呢", "你妈妈为什么要养活你", "为什么要养你这只畜生呢", "为什么把你养大你不好好做人", "而跑去做狗呢?回家问你妈妈去", "这么多为什么因为",
			"你知道究竟是为什么呢", "是因为你这条狗惹的祸呢", "你难道还不知错么", "为什么还坚持呢", "你坚持的理由在哪", "为什么还不变回原形呢", "孩子你知道了么,还是做人好呢",
			"做一个低调的网络人物才能受到别人的尊重", "我知道你怕我了呢嘻嘻", "你为什么要怕我呢", "你可不可以不怕我呢", "我不想骄傲使人退步发生在爹身上呢", "你手心为什么出汗呢", "你键盘为什么会湿了呢",
			"你为什么会有这种现象呢", "你真的紧张了是么", "你有感觉到冷么", "你在发抖么", "你放松一点好不好啊", "你爹爹我很仁慈的", "你别让感觉到你是在逃避我好么", "我不想离开你呢你知道吗",
			"从你身上爹找到无穷的自信了呢", "你给爹点激情吧", "你让我知道你的速度也是多么多么快", "语言是多么多么丰富吧", "你为什么这么这么巴拉巴拉的逃避我呢", "你给个解释听听好么",
			"我真的不想被别人议论成我是在欺负你", "你为什么哭呢", "你让我看到你的眼泪了", "你能成熟点好么", "你竟然还装不知道你在这样无力地打击我", "你觉得你有速度还是怎么的你可以出来和我抵抗吗",
			"觉得你完全没有速度你明白的还是怎么的你可以出来吗", "我觉得你完全没有水平你明白的还是怎么的你可以出来吗", "你可以出来和我抵抗的吗还是怎么的你告诉我的可以吗",
			"你难道不知道你就是没有速度垃圾吗你告诉我的呢", "你难道不知道你就是没有速度的垃圾吗你出来的呢", "你完全没有速度和蜗牛龟龟似的你明白的还是怎么的啊", "你难道不知道你就是没有速度的垃圾吗你告诉我的呢",
			"你难道不知道你就是完全没有速度的垃圾吗你告诉我的可以", "你麻痹的是不是可会造谣我了可有意思了呢小伙子", "纹丝不动对不对了啊是不是动不动窝囊废了啊", "你现在能不能登峰造极了啊你马上的开始啊",
			"你个小屁孩子你马上的和我比速度啊你行不行", "你现在能不能登峰造极了啊你马上的开始啊", "你现在嬉皮笑脸的干什么啊", "你这样什么意思啊", "你现在是不是气急败坏咬牙切齿了呢",
			"你现在能接你父亲我华丽又神圣的语言吗", "你现在开始和你父亲我唠嗑了吗", "你现在开始欺负爹爹了是吗", "你现在开始大逆不道了是吗", "你这坨只能被人踩在脚底下的烂泥你知道了吗",
			"你不知道你父亲我的唠嗑是出神入化的吗", "你现在什么东西你和你父亲我叫嚷什么了啊", "你完全的不懂你现在的速度了是不是呢", "你完全不知道你是什么词汇你奶奶孙子脱口而出",
			"我有没有懂不懂速度你自己不清楚吗", "你语无伦次你妈妈个b啊你个大垃圾", "你完全完全你妈妈个老篮子东西", "你难道完全的不清你是什么东西了是不是呢",
			"你难道不觉得你现在没道路没技术没水平没战斗力没杀伤力吗。", "你没有可以挑战我的词汇了是吗小老弟", "你现在要是有一点攻击力我都服你了啊", "你完全的不能跟我抗衡你知道吗小伙子",
			"你现在配有资格让我挑战你吗", "你完全的不行了你的词汇丝毫没杀伤力", "你现在衡量什么东西啊", "你完全没有能力你自己清楚了吗", "你完全的不能跟我的等级相提并论你知道吗", "你完全的不行什么东西了啊",
			"你现在完全不能你奶奶个腿儿啊", "你还相提并论了吗", "你完全没速度什么战斗力大逆不道的话你都比什么攻击了啊", "你完全的不了解你是什么东西的速度了", "你现在叫花子似的说什么戳穿啊",
			"你现在癞皮狗似的你说什么速度等级啊", "你完全的不知道你的处女膜是怎么破的", "你噶骄什么呢你篮子又干嘛呢你想升级是吗", "你完全的不了解你现在是不是完全的没速度",
			"你是不是完全的不懂你的等级和我的等级有多大的差距了是不是呢", "你完全的不了解我的等级有多高你就挑战我和我抗衡了是不是呢", "你现在什么东西你和我嗷嗷什么东西了啊.", "你衡量你奶奶孙子啊",
			"我现在的速度完全都可以挑战你那0杀伤力的蛤蟆速度了", "你是垃圾的你自己不清楚吗", "你说什么东西呢啊", "你完全的不知道你爷爷都不是我对手你又了解知道吗", "你是不是完全不知道呢",
			"你现在完全没速度了啊", "你爷爷你妈妈个b啊", "你个垃圾你了解你奶奶孙子啊", "你完全不晓得我是不是很厉害的等级挑战你那蜗牛的速度了是不是呢", "你现在速度什么东西了啊", "我是你父亲你知道吗",
			"你谈什么垃圾速度啊", "你还跟我操你妈你是什么东西呢", "你以为你登峰造极了是吧", "你以为我不是你父亲了是吧", "你就是完全的蛤蟆速度我跟上你了", "你速度你妈妈个b啊你个垃圾",
			"你还完全你自己草你妈你自己清楚了吗", "我是你父亲你还登峰造极了", "你清楚你妈妈个b", "你完全了就你妈妈个b", "你张口闭口奶奶孙子啊", "你现在开始说些什么东西大逆不道的话", "你自己不清楚吗",
			"你是不是完全的又没有词汇了呢", "你是不是头脑一片混乱了", "你是不是想抄袭我的高级词汇了呢", "你开始说些什么东西你自己不清楚吗", "你就想完全地逃避我你知道了吗",
			"你快告诉我你是不是因为没有速度而沉默呢", "你就是不是又开始你那个泛滥了", "小朋友你现在和我说些什么乱七八糟的话啊", "我看你的老词汇要养到多少岁才能有点战斗力", "是不是呢回答我",
			"你完全不清楚事实", "你的词汇已经很落伍了", "你现在是不是完全的不清楚了", "你为什么现在完全跟不上我的速度了", "你是大脑不清晰了还是欲言又止了", "你那词汇到底是百度呢还是搜狗呢",
			"你现在是不是气急败坏地苦苦搜寻所谓的词汇了是不是呢", "你是不是完全的不知道你多搞笑是不是呢", "我的唠嗑那么牛比怎么是废物呢不是吗", "你就知道幻想吧", "你是不是完全的不行了呢",
			"你是不是没速度加没词汇啊", "你选迮繁荣什么都不是你什么废物似的", "你是不是很喜欢你那个词汇呢你是不是完全的跟不上我的速度了", "你是不是很喜欢呆驴一样的速度了", "不要扯开话题和你父亲我唠嗑啊",
			"你是不是完全的登峰造极的窝囊废了呢", "你是不是完全的不清楚你是什么东西了是不是呢", "你自己半身不遂了呢你清楚了吗", "是不是完全的不清楚你说不上话了是不是呢", "你现在大逆不道的话太多了你知道了吗",
			"你是不是词汇找到了呢", "是不是呢你告诉我呢你完全的不清楚了", "你是不是这样的呢你告诉我呢", "能不能告诉我你现在是个什么东西了啊", "你想让我告诉你什么东西你自己说啊",
			"哈哈你的词汇是什么处女膜的呢", "你就告诉你我完全速度一五一十的揍一年了啊", "你这个速度你是在散步呢吗", "我的速度是完全的揍你你自己不知道了吗", "你什么三三两两什么东西乱七八糟的啊",
			"你就告诉我你是不是很喜欢抄袭呢是不是的", "我抄袭什么的下列啊你个垃圾似的你清楚了吗", "你选杂开始大逆不道的话越来越多了你知道了吗", "你刚刚看到你那些编造的话语都是什么东西了啊弟弟你可真能幻想啊",
			"你现在怎么跟我抗衡你还关呢", "你是不是完全的没有词汇了还是在想怎么找词汇呢", "你现在不清楚我词汇什么东西了啊", "你是个什么半身不遂的东西啊", "你是不是整一个弟弟东西啊",
			"你不行了你老了你知道吗", "你跟不上我的速度是不是吧", "完全什么东西你个弟弟你妈妈个b啊", "你什么东西你自己不清楚吗", "你自己没词汇对不对啊", "你妈死了是不是", "你妈死了知道不",
			"你鬼哭狼嚎叫妈妈呢", "你丢弃了这个半死不活的妈妈啊", "放心我会一五一十告诉你妈", "然后你开始给我反抗好吗", "你没有一点速度啊你没什么实力怎么和我抗衡",
			"你开始死皮赖脸的耀武扬威了呢你看谁给你脸了", "是谁给你的勇气啊你告诉我", "你这个速度简直就是蜗牛啊", "我在侮辱你呢", "你没什么脾气啊", "还在那嘻嘻哈哈什么呢", "你气急败坏什么呢",
			"是不是恼羞成怒了", "你照照镜子看看你这个人模狗样的好吗", "你难道不知道吗", "你就是我的手下败将", "小老弟给我反抗", "我给你机会反抗", "你什么窝囊废自己窝囊废你比比叨叨什么啊",
			"你开始怀疑爸爸开软件了", "嘻嘻嘻嘻弟弟开始造谣你爹开软件了哈", "你继续造谣你爸爸", "你再怎么造谣我我还是你野爹啊", "野爹就是外面操翻你妈了然后不负责了你知道吗", "你是不是狗啊",
			"你就是一条废狗啊", "这你清清楚楚的啊", "我正在随随便便殴打你这个二哈呢你知道吗", "你没有一点速度还在这丢人现眼呢", "你妈生你干什么玩意", "自己垃圾还瞎扒拉啥词汇啊",
			"滚吧死垃圾没速度还在这你在侮辱你爹的啊", "你是不是气急败坏", "你是不是恨不得冲过来一拳打倒我啊", "你为什么不拿出你的词语驳回我的理论呢", "你难道完全忘记了你是什么东西了是不是呢",
			"你难道不觉得你的词汇", "支离破碎零零散散流离失所无家可归", "没有道理没有技术没有水平没战斗力没杀伤力吗", "你完全的没速度的词汇能挑战我是吗", "你现在能有一点攻击力我都服你了啊",
			"你完全的不能跟我抗衡你知道吗小伙子", "你现在配有资格让我挑战你吗", "你完全没速度什么战斗力大逆不道的话你都比什么攻击了啊", "你完全的不了解你是什么东西的速度了你现在叫花子似的说什么戳穿啊",
			"你现在癞皮狗似的你瞎扯什么速度等级啊", "你完全不知道你的处女膜是怎么破的你瞎叫什么呢", "你个篮子在干嘛呢你想升级是吗", "你完全不了解情况你现在完全没速度",
			"你是不是完全的不懂你的等级和我的等级有多大的差距是不是呢", "你完全的不了解我的等级有多高你就挑战我和我抗衡了是不是呢", "你现在什么东西你跟我嗷嗷叫什么东西了啊",
			"你就是完全的速度还跟我完全比你那没用的速度了是不是呢", "你现在速度什么东西了啊我是你爹爹你知道了吗", "你谈什么垃圾速度啊你还完全的跟我处女膜什么呢", "你以为你登峰造极了是吧",
			"你就是完全没有速度你跟上我了吗", "你速度你妈妈个b啊你个小垃圾", "你还完全不清楚你现在自己草你自己妈你自己知道了吗", "我是你亲亲爹爹你还以为自己登峰造极了",
			"你清楚你妈妈个b啊你完全了就你妈妈个b啊你生你奶奶孙子啊", "你是不是完全的登峰造极的窝囊废了呢", "你是不是完全的不清楚你是什么东西了是不是呢", "你自己半身不遂了你清楚了吗",
			"是不是完全的不清楚你说不上了是不是呢", "你现在大逆不道的话很多你知道了吗", "你是不不是词汇找到了呢是不是完全的不清楚", "你说跟不上你自己不清楚吗", "是不是呢你告诉我呢",
			"你完全不清楚了你是不是这样的呢你告诉我呢", "你还知道词汇了呢你是不是这样你告诉我", "你想让我告诉你什么东西你自己说啊", "哈哈你的词汇是什么处女膜的呢你", "我完全速度一五一十的揍你一年了啊",
			"你以为你三三两两的词汇能把我按在地上暴打呢", "我的速度是把你吊起来打你自己不知道吗", "你那些都是什么三三两两什么叽叽喳喳什么乱七八糟的东西啊",
			"你就告诉我你是不是很喜欢抄袭呢是不是呢我抄袭什么的词汇啊", "你个垃圾似的你清楚了吗", "你那杂交词汇变得越来越多了你知道吗", "你刚刚看道袍你超次什么东西了啊你可真能幻想啊",
			"你现在怎么跟我抗衡你还关呢我现在怎么不行了啊", "你拼死拼活拼不过我一五一十的速度啊", "你是不是完全的没有词汇了还是在想怎么找词汇呢", "你现在不清楚我词汇是什么东西了啊",
			"你回答我你现在是不是个半身不遂的东西啊", "你现在怎么了呢你是不是完全的弟弟啊", "你不行了你老了你知道吗", "你跟不上我的速度吧", "你什么东西你个弟弟你妈妈个b啊", "你什么东西你自己不清楚啊",
			"你自己没有一点词汇对不对啊", "你就是二愣子似的你还说什么啊弟弟", "你一个二愣子弄出这么多话题弟弟你好好想想把", "没速度就不要恶心你爸爸我你明白吗", "你爸爸我的速度揍你足够了你知道吗",
			"你就是二愣子似的你还说什么啊弟弟", "你一个二愣子弄出这么多话题弟弟", "你好好想想把你怎么提升速度", "我觉得你完全没速度你自己清楚你那拖拉机速度", "我觉得你完全没速度你弟弟噶哈你自己清楚垃圾啊",
			"我草你妈你这样语无伦次的bb什么东西", "你就是狗你知道不知道啊", "我草你妈的你干什么三三两两的啊弟弟你自己垃圾啊", "小家伙你自己马上出来跟我唠嗑可以么篮子似的呢啊",
			"你这样的人我瞧不起你阿你自己没发现还是怎么的啊", "你自己就是个拖拉机似的你还跟我近乎的哈巴狗啊", "你自己就是个拖拉机跟我套近乎的小家伙什么哈巴狗啊", "你自己充其量严格二愣子弟弟啊你还完事瞧不起我啊",
			"你自己没有战斗力你别跟我扯淡可以吗.你完全没有战斗力啊", "你自己随便拖拉机你跟我套近乎的小家伙你哈巴狗的啊", "你自己没有战斗力你别跟我墨迹可以吗你完全没有战斗力啊",
			"你一丁点战斗力都没有你还跟我抗衡是这样吗弟弟", "你的眼睛开始发昏了是不是啊", "你跟我墨迹啥啊你完全没有战斗力啊你了解吗", "你难道不觉得什么了吗你自己完全充其量篮子似的啊",
			"你自己完全没有战斗力的呢啊", "你自己完全二愣子似的啊弟弟", "你完全废物啊是这样的情况吗", "你你自己完全拖拉机似的啊", "你充其量就是二愣子似的啊", "你现在完全没有速度了啊",
			"你自己拖拉机似的啊你自己难道不清楚什么了吗", "你自己完全拖拉机似的啊我可瞧不起你这样的人啊", "你自己清楚什么了啊你自己完全拖拉机似的啊", "你自己没有战斗力你清楚了吗我可瞧不起你这样啊",
			"你自己随便拖拉机你自己难道不清楚什么了吗", "你自己不清楚什么情况了吗你自己完全蜗牛似的啊", "你自己完全拖拉机似的啊你自己完全没有战斗力啊", "你自己没有战斗力你别跟我墨迹可以么篮子似的啊",
			"你自己一个颤巍巍的小伙子你还和我墨迹什么了啊", "你自己充其量就是个二愣子似的啊你自己清楚了吗", "你自己现在整一个二愣子似的啊你难道不清楚什么了吗", "弟弟你自己完全没有速度似的难道不对吗废物啊",
			"弟弟你自己现在好象我儿子似的你清楚什么了吗", "弟弟你自己蜗牛速度似的难道不对吗废物哈", "弟弟你自己没有战斗力似的你难道不清楚什么了吗", "弟弟你自己完全拖拉机似的啊这样你清楚情况了吗",
			"弟弟你自己充其量篮子似的啊你完全没有战斗力啊", "弟弟你自己就是二愣子似的啊你自己没觉得了吗", "弟弟你自己就像拖拉机似的我可瞧不起你这样的人啊", "你现在完全二愣子似的啊你难道不清楚什么了吗",
			"你自己充其量演个二愣子弟弟啊", "你完事你还瞧不起我啊是不是啊回答我啊", "你自己没有战斗力你别跟我墨迹可以吗你完全没有战斗力啊", "你一丁点战斗力都没有你还跟我抗是这样吗弟弟",
			"你自己一个颤巍巍小伙子你跟我墨迹啥啊弟弟", "你自己没有战斗力你别跟我扯淡可以吗你自己废物啊", "你自己拖拉机似的啊你自己难道不清楚什么了吗", "你自己就是个拖拉机跟我套近乎的小家伙什么哈巴狗啊",
			"你现在完全准备好和我唠嗑了是吗我可瞧不起你啊", "你现在自己是不是没有准备好了啊你自己清楚了吗", "你自己随便拖拉机你跟我套近乎的小家伙你哈巴狗的啊", "你现在自己没有战斗力啊是这样的情况吗小家伙",
			"小家伙你自己马上出来跟我唠嗑可以么篮子似的呢啊", "你这样的人我瞧不起你啊你自己没发现还是怎么的啊", "你现在充其量没有战斗力你清楚这样的情况了吗", "我现在觉得你这样的速度完全不可以跟我对抗啊不是吗",
			"你完全就是一个拖拉机啊你不知道我是你爸爸吗", "我可瞧不起你这样的啊你难道不清楚你是儿子了吗", "你难道不清楚什么了吗你自己完全拖拉机似的啊", "我可不喜欢搭理你这样的啊你自己完全清楚了啊",
			"你一丁点战斗力的没有你还跟对抗是这样的呢吗", "你现在自己好象阿拉伯难民啊你难道不是这样的情况吗", "小弟弟我觉得你这样的速度完全不可以跟我对抗不是吗",
			"你自己现在意大利的警犬似的你难道不是这样的情况了吗", "你现在是不是自己无能为力是不是", "你现在是不是无济于事了对不对", "你现在是不是大言不惭了是不是", "你现在是不是闻风丧胆了是不是",
			"你现在是不是错字连篇大神对不对", "你现在是不是大言不惭哈巴狗没讲错吧", "你现在是不是无济于事是不是的", "小伙子你现在是不是无能为力对啊", "你怎么了窝囊废弟弟", "你自己窝囊废", "怎么窝囊废",
			"什么窝囊废", "为什么窝囊废", "是不是窝囊废", "你回答爸爸的啊", "你干什么半身不遂", "你唧唧喳喳的有意思啊", "你干什么窝囊废弟弟", "什么什么窝囊废", "你耀武扬威什么啊",
			"有意思啊", "小伙子你什么东西", "你胡说八道什么", "你自己是不是窝囊废", "怎么了窝囊废似的", "你完全窝囊废", "你知道吗窝囊废", "你知道什么了啊", "你自己是不是窝囊废",
			"草你妈窝囊废", "你怎么了啊窝囊废", "你干什么耀武扬威啊", "你半身不遂窝囊废啊", "你怎么了啊", "你回答爸爸我啊", "你怎么了啊窝囊废", "你干什么沉默的啊", "怎么了窝囊废啊",
			"是不是唧唧喳喳的", "为什么窝囊废的", "弟弟你是不是窝囊废的", "告诉爸爸好不好窝囊废", "三三两两窝囊废的", "稀稀拉拉窝囊废的", "小伙子你干什么耀武扬威", "你个杂交东西",
			"你大言不惭什么呢哈巴狗", "你干什么窝囊废小伙子", "你个半身不遂的小伙子", "我草你妈的有意思了对不对", "你是不是哈巴狗东西啊神经病", "为什么没有速度你告诉你爸爸我",
			"为什么窝囊废你告诉你爸爸我啊", "窝囊废东西你还不滚", "你恶心了你爸爸我", "你为什么在这里耀武扬威", "为什么在这里唧唧喳喳", "为什么在这里汪汪汪地狗叫", "为什么为什么为什么我草你妈的",
			"你为什么为什么语无伦次", "你告诉你爸爸我", "告诉我为什么", "你告诉我你什么东西", "你耀武扬威的什么东西", "你是不是没有速度", "没有速度你装什么B攻击我",
			"你告诉我你哪来的自信与勇气拿着这点词汇来攻击我", "怎么不告诉我啊我告诉你啊你赶紧告诉我啊", "你是个什么东西啊，你是不是小垃圾啊", "你为什么不告诉我你是个小垃圾呢", "回答我啊弟弟",
			"我現在在這埋汰妳呢小老弟", "妳個沒速度的小廢物", "我好象妳爸爸似的妳難道自己不清楚現在的情況嗎", "然後妳完全沒有力量妳明白妳的扣字垃圾嗎小廢物", "我好象妳爸爸似的妳是不了解情況怎麽的呢",
			"妳爲什麽在這裏耀武揚威的", "現在了解什麽情況妳完全垃圾似的知道了嗎", "妳好象垃圾似的妳怎麽和我對抗呢", "嘻嘻嘻嘻妳能出來告訴我嗎", "妳完全沒有速度妳明白什麽情況嗎垃圾似的出來找揍",
			"妳自己好象我兒子似的只能汙蔑我還是想怎麽樣嗎", "我就是妳父親似的隨便毆打妳壹個完全沒什麽力量的兒子", "父親我隨便毆打妳這樣的事實情況了啊兒子", "我現在不是隨便毆打妳嗎自閉青年",
			"妳自己現在不了解情況還是怎麽的", "自閉症兒童開始毆打妳登峰造極的爸爸了啊", "我怎麽感覺妳和我的自閉兒子似的反抗爸爸", "兒子妳現在自己怎麽反抗的爸爸的妳知不知道什麽情況啊",
			"現在的妳看見妳爹的各種速度害怕了還是怎麽的", "爸爸隨意的打的妳了啊妳清楚事實嗎", "妳還想無力地反抗我還是怎麽啊弟弟", "妳自己不了解情況還是怎麽的妳完全垃圾似的", "嘻嘻嘻妳的b臉呢",
			"妳媽給妳b臉妳裝妳媽b裏去了呢", "妳好像完完全全不清楚情況", "妳沒有發現妳根本不是爹爹的對手嗎", "爹爹我隨隨便便幹翻妳母親", "妳個狗兒子只有畏畏縮縮的滾了", "妳現在是不是坐在電腦前手心出汗呢",
			"妳是不是緊張了呢妳是不是語無倫次了呢", "妳現在能做的只有亂敲鍵盤滿懷恐懼地向我暗示妳內心的恐懼呢", "妳沒有發現現在就像壹個跳梁小醜壹樣取悅我嗎", "我隨隨便便毆打妳毆打妳個自閉東西",
			"爲什麽爲什麽動不動操妳媽傻逼", "這是妳翻來覆去的詞彙呢", "小朋友拿出妳的實力來可好", "妳沒有發現妳根本不是爹爹的對手嗎", "妳是不是死皮賴臉的給我上啊", "死了媽的小夥子啊",
			"妳別在無濟于事的給妳爸爸我死皮賴臉的上了", "妳就是死皮賴臉的小夥子是不是這樣妳快回答我", "妳是個啥啊妳個死了媽的小夥子妳就是個小廢物啊", "小夥子妳八仙過海的趕來狐假虎威地狗妳爹爹我呢",
			"妳是不是垃圾狗籃子啊妳告訴大家妳是不是啊", "妳是不是大言不慚地吹噓啊垃圾狗籃子呢", "妳怎麽了啊是不是畏懼妳爸爸我了啊", "妳個狗籃子是不是啊小夥子妳媽死了",
			"妳承不承認妳是個耀武揚威半身不遂的垃圾狗籃子啊", "妳趕緊的跟上啊", "是不是沒有辦法提高妳的速度了", "自己是不是已經感覺無能爲力了啊", "是不是沒有速度焦頭爛額了啊",
			"妳是不是知道妳自己要輸給妳登峰造極的爸爸我了啊", "妳是不是沒有自知之明了啊", "妳覺得妳要輸給妳登峰造極的爸爸我啊妳開始不是風風火火的嗎", "妳幹什麽啊墨迹什麽啊妳什麽自閉症速度啊",
			"妳難道不應該用強大到令人退縮的詞彙來攻擊我嗎", "弟弟妳怎麽詞窮了啊", "窩囊廢東西是不是啊小夥子", "妳還大言不慚地吹噓妳的速度是不是啊", "妳有什麽資本吹噓",
			"妳無人問津的速度啊妳壹五壹十的告訴妳爸爸我啊", "妳是不是懸心吊膽的以爲妳可以贏過妳登峰造極的爸爸我啊", "我看妳沾沾自喜的樣子妳是不是以爲妳可以勝利啊", "小夥子妳是不是窩囊廢弟弟啊",
			"妳是不是家喻戶曉的耀武揚威的不可壹世的窩囊廢啊", "妳怎麽成了壹個甕中之鼈啊", "妳是不是瘋瘋癫癫無法自拔了啊", "窩囊廢東西是不是妄自尊大啊", "妳爲什麽怎麽用妳哩哩啦啦的言語攻擊妳登峰造極的爸爸我啊",
			"妳是不是想用妳婆婆媽媽的詞彙擊敗妳老老實實的爹爹我啊", "妳是不是開始沾沾自喜了啊", "妳是不是以爲妳可以擊敗登峰造極的爸爸我啊", "妳現在是不是覺得莫名其妙的啊", "爲什麽妳爹打字速度這麽快啊",
			"妳是不是畏懼妳爹爹的速度了啊", "妳是不是對這些詞彙壹竅不通啊", "妳是不是什麽都不懂啊"};

	public static boolean lagcheck = false;
	public static List<String> wdred = new ArrayList<String>();
	private final TimeHelper timer = new TimeHelper();
	
	private final Value<Boolean> wdr = new Value<Boolean>("Collective_AutoWdr", true);
	private final Value<Boolean> lgaback = new Value<Boolean>("Collective_LagBack", true);
	private final Value<Boolean> autol = new Value<Boolean>("Collective_AutoL", false);
	private final Value<Boolean> gg = new Value<Boolean>("Collective_AutoGG", true);
	private final Value<Boolean> autoplay = new Value<Boolean>("Collective_AutoPlay", false);
	private final Value<Boolean> disable = new Value<Boolean>("Collective_AutoDisable", false);
	private final Value<Boolean> abuse = new Value<Boolean>("Collective_Abuse", true);
	private final Value<Boolean> ad = new Value<Boolean>("Collectivet_AD", false);
	
	@EventTarget(events = {EventChat.class, EventPacket.class})
	private final void onEvent(Event e) {	
		//EntityLivingBase target = KillAura.target;
		if(e instanceof EventChat) {
			EventChat ec = (EventChat)e;
			Random r = new Random();
			String fuck = " ";
			if((this.abuse.getValueState()).booleanValue()) {
				fuck = intcihui[r.nextInt(800)];
			}
			boolean canPlayer = ec.getMessage().contains("被" + mc.thePlayer.getName() + "击杀")
					|| ec.getMessage().contains("被" + mc.thePlayer.getName() + "扔下了虚空")
					|| ec.getMessage().contains(" 被击杀，击杀者： " + mc.thePlayer.getName())
					|| ec.getMessage().contains(" 被扔下悬崖，击杀者： " + mc.thePlayer.getName())
					|| ec.getMessage().contains(" 被扔下虚空，击杀者： " + mc.thePlayer.getName())
					|| ec.getMessage().contains("被" + mc.thePlayer.getName() + "扔下了悬崖。"); 
			if(this.autol.getValueState().booleanValue() & canPlayer) {
				mc.thePlayer.sendChatMessage("/ac [" + Oxygen.INSTANCE.CLIENT_NAME + "]" + ec.getMessage().split("被")[0] + " " + "L " + fuck);
			}
		
			if(this.wdr.getValueState().booleanValue() & canPlayer) {
				mc.thePlayer.sendChatMessage("/wdr "+ ec.getMessage().split("被")[0] +" ka fly reach nokb jesus ac");
			}
		}
		
		if(e instanceof EventPacket) {
			EventPacket ep = (EventPacket)e;
			if (ep.getPacket() instanceof S08PacketPlayerPosLook) {
				if(this.lgaback.getValueState().booleanValue()) {				
	                Collective.lagcheck = true;
				}else {
					S08PacketPlayerPosLook look = (S08PacketPlayerPosLook)ep.getPacket();
		            ((IS08PacketPlayerPosLook)look).setYaw(mc.thePlayer.rotationYaw);
		            ((IS08PacketPlayerPosLook)look).setPitch(mc.thePlayer.rotationPitch);
		            Collective.lagcheck = false;
				}
			}
			
			if (ep.getPacket() instanceof S45PacketTitle) {
				S45PacketTitle packet = (S45PacketTitle) ep.getPacket();
				if (packet.getType().equals(S45PacketTitle.Type.TITLE)) {
					String text = packet.getMessage().getUnformattedText();
					String ad = " ";
					if(this.ad.getValueState().booleanValue()) {
						 ad ="Copy Yes!";
					}
					if (text.equals("胜利！")) {
						if(this.gg.getValueState().booleanValue()) {
						mc.thePlayer.sendChatMessage("/achat " /*+ "[" + Oxygen.INSTANCE.CLIENT_NAME + "]"*/ + "GG "+ ad);
						}
						if(this.disable.getValueState().booleanValue()) {
							Oxygen.INSTANCE.ModMgr.getModule(KillAura.class).set(false);
							Oxygen.INSTANCE.ModMgr.getModule(InvCleaner.class).set(false);
							ClientUtil.sendClientMessage("AutoDisable!", Type.ERROR);
						}
						if(this.autoplay.getValueState()) {
							if(this.timer.isDelayComplete(3000L)) {
								this.timer.reset();
								Oxygen.INSTANCE.ModMgr.getModule(FastPlay.class).set(true);
							ClientUtil.sendClientMessage("AutoPlay!", Type.INFO);
							}
						}
					}
					if (this.disable.getValueState().booleanValue())  {
						if(text.equals("你死了！")) {
							if(Oxygen.INSTANCE.ModMgr.getModule(KillAura.class).isEnabled()) {
								Oxygen.INSTANCE.ModMgr.getModule(KillAura.class).set(false);
							}
							if(Oxygen.INSTANCE.ModMgr.getModule(InvCleaner.class).isEnabled()) {
								Oxygen.INSTANCE.ModMgr.getModule(InvCleaner.class).set(false);
							}
						ClientUtil.sendClientMessage("AutoDisable!", Type.ERROR);
						}
				}
				}
			}
		}
}		
	
}
