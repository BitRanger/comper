package test.gen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.wangk.comper.feature.Config;
import org.wangk.comper.feature.IEvaluator;
import org.wangk.comper.feature.IRandomGenerator;
import org.wangk.comper.feature.SystemConfig;
import org.wangk.comper.feature.TrainingField;
import org.wangk.comper.feature.impl.Evaluator;
import org.wangk.comper.feature.impl.RandomGenerator;
import org.wangk.comper.feature.impl.Trainer;
import org.wangk.comper.feature.model.Group;
import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.util.Pair;

import test.comper.jdbc.DBConnection;

public class Demo {

	public static void main(String[] args) {
		
		DBConnection connection = new DBConnection();
		connection.connect();
		///*
		IEvaluator evaluator = new Evaluator();
		Trainer trainer = new Trainer();
		trainer.setQuestionSerive(connection.questionService);
		
		IRandomGenerator rGenerator = new RandomGenerator();

		TrainingField field = new TrainingField();
		field.setEvaluator(evaluator);
		field.setRandomGenerator(rGenerator);
		field.setTrainer(trainer);
		
		Random random = new Random();
		Set<Integer> chaps = new HashSet<>();
		//考察10个知识点
		while (chaps.size() < 10) {
			chaps.add(random.nextInt(16));
		}
		chaps.remove(0);
		
		Map<QuestionType, Pair<Integer, Integer>> sec = new HashMap<QuestionType, Pair<Integer,Integer>>();
		//10道选择题， 共20分
		sec.put(QuestionType.MULTI_CHOICE, new Pair<Integer, Integer>(30, 15));

		//15道填空题， 共15
		sec.put(QuestionType.FILL_BLANKS, new Pair<Integer, Integer>(15, 15));
		// 3道解释题，共15分
		sec.put(QuestionType.EXPLAINATION, new Pair<>(15, 3));
		
		//6道简答， 30分
		sec.put(QuestionType.SIMPLE_QA, new Pair<>(15, 3));
		
		//1 道应用题
		sec.put(QuestionType.APPLICATION, new Pair<>(20, 1));
		
		// 生成本次的设置（难度 0.6，容忍度0.4
		Config config = 
		Config.build(0.6F, chaps, sec, 5, 0.34F, SystemConfig.getDefault());
		
		field.setConfig(config);
		field.prepare();
		field.train();
		List<Group> groups = field.getSortedResult();

		//输出最佳的一组
		for (Group group : groups) {
			System.out.println(group.summary.adaptability);
		}
		System.out.println(groups.get(groups.size() - 1));
//		System.out.println(groups.get(groups.size() - 2));
		//*/
	}

}
