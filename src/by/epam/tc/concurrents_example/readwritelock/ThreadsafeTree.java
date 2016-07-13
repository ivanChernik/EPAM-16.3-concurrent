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
	// ������ ��� ������
	private static Map<Integer, String> levelSpacesMap = new ConcurrentHashMap<Integer, String>();
	// ������ ������
	private TreeNode root = new TreeNode();

	//���������� ������
	public void put(String s) { 
		TreeNode v = root;
		lock.writeLock().lock(); 
		
		for (char ch : s.toLowerCase().toCharArray()) {
			// ���� � ������������� �������� ��� ��� ������ ��������� ��������
			// ��������� ���
			if (!v.getChildren().containsKey(ch)) {
				v.getChildren().put(ch, new TreeNode());
			}
			// ���������� �� ���� ������� � ������
			v = v.getChildren().get(ch);
		}
		// ��������� ������ ������ ��������������� ������
		v.setLeaf(true);

		lock.writeLock().unlock();
	}

	// ���� ����� � ������
	public boolean find(String s) {
		TreeNode v = root;
		boolean result = true;
		lock.readLock().lock();
		// �����
		for (char ch : s.toLowerCase().toCharArray()) {
			// ���� � ������������� �������� ��� ������ ��������� ��������
			// ������ � ������ ��� ������� ������
			if (!v.getChildren().containsKey(ch)) {
				result = false;
				break;
			}
			// ���� � ������������� �������� ���� ����� �������� ������� ��
			// ������������� ��� ������������ � ���������� �����
			else {
				v = v.getChildren().get(ch);
			}
		}
		lock.readLock().unlock();
		return result;
	}

	// ������ ��� ���������� ������ � ������
	private static String getSpace(int level) { 
		
		// ��������� �������
		String result = levelSpacesMap.get(level); 
		
		// ���� ��� ������ ������ ��� ��� ������� - ������� �����
		if (result == null) { 
			StringBuilder sb = new StringBuilder(); 
			 // ���������� �������� = ������
			for (int i = 0; i < level; i++) {
				sb.append(" ");
			}
			result = sb.toString();
			levelSpacesMap.put(level, result); 
		}
		return result;
	}

	// ����� ������ � �����������
	public void printSorted() { 
		lock.readLock().lock();
		print(root, 0);
		lock.readLock().unlock(); 
	}

	// ����� �������� ��������� � ������� � �������� �������
	private void print(TreeNode node, int level) { 
													
		for (Character ch : node.getChildren().keySet()) {
			
			//�������� ������� ������� � ��������
			LOGGER.debug(getSpace(level) + ch); 
			//������� ��� �������� ��������
			print(node.getChildren().get(ch), level + 1); 
		}
		// ���� ������� �������� ������ �� ������� ������ ������
		if (node.isLeaf()) { 
			LOGGER.debug("that list"); 
		}
	}

}
