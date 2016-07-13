package by.epam.tc.concurrents_example.readwritelock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ThreadsafeTree {
	private final static Logger LOGGER = LogManager.getRootLogger();
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	// отступ для уровня
	private static Map<Integer, String> levelSpacesMap = new ConcurrentHashMap<Integer, String>();
	// корень дерева
	private TreeNode root = new TreeNode();

	//добавление строки
	public void put(String s) { 
		TreeNode v = root;
		lock.writeLock().lock(); 
		
		for (char ch : s.toLowerCase().toCharArray()) {
			// если у родительского элемента ещё нет такого дочернего элемента
			// добавляем его
			if (!v.getChildren().containsKey(ch)) {
				v.getChildren().put(ch, new TreeNode());
			}
			// опускаемся на один уровень в дереве
			v = v.getChildren().get(ch);
		}
		// последний символ строки устанавливается листом
		v.setLeaf(true);

		lock.writeLock().unlock();
	}

	// ищем строк в дереве
	public boolean find(String s) {
		TreeNode v = root;
		boolean result = true;
		lock.readLock().lock();
		// поиск
		for (char ch : s.toLowerCase().toCharArray()) {
			// если у родительского элемента нет такого дочернего элемента
			// значит в дереве нет искомой строки
			if (!v.getChildren().containsKey(ch)) {
				result = false;
				break;
			}
			// если у родительского элемента есть такой дочерний элемент то
			// устанавливаем его родительским и продолжаем поиск
			else {
				v = v.getChildren().get(ch);
			}
		}
		lock.readLock().unlock();
		return result;
	}

	// отступ для некоторого уровня в дереве
	private static String getSpace(int level) { 
		
		// получение отступа
		String result = levelSpacesMap.get(level); 
		
		// если для такого уровня ещё нет отступа - создаем новый
		if (result == null) { 
			StringBuilder sb = new StringBuilder(); 
			 // количество пробелов = уровню
			for (int i = 0; i < level; i++) {
				sb.append(" ");
			}
			result = sb.toString();
			levelSpacesMap.put(level, result); 
		}
		return result;
	}

	// вывод дерева с сортировкой
	public void printSorted() { 
		lock.readLock().lock();
		print(root, 0);
		lock.readLock().unlock(); 
	}

	// вывод дочерних элементов у вершины с заданным уровнем
	private void print(TreeNode node, int level) { 
													
		for (Character ch : node.getChildren().keySet()) {
			
			//дочерний элемент вывести с отступом
			LOGGER.debug(getSpace(level) + ch); 
			//вывести все дочерние элементы
			print(node.getChildren().get(ch), level + 1); 
		}
		// если вершина является листом то вывести пустую строку
		if (node.isLeaf()) { 
			LOGGER.debug("that list"); 
		}
	}

}
