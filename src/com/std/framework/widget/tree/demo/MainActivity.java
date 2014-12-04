package com.std.framework.widget.tree.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.std.framework.widget.tree.TreeNode;
import com.std.framework.widget.tree.TreeView;
import com.std.framework.widget.tree.TreeView.TreeAdapter;

 class MainActivity extends Activity{
	private TreeView treeView;
	private List<TreeNode> nodes;
	private TreeAdapter treeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		treeView = (TreeView)findViewById(R.id.treeView1);
//		treeView.setExpandLevel(2);
//		treeView.setLeafImageResourceId(R.drawable.ic_launcher);
//		SampleData();
//		treeView.setAdapter(treeAdapter);
	}
	
	private void SampleData(){
		TreeNode root = new TreeNode("00", "Ŀ¼");
		TreeNode TreeNode_lv1 = new TreeNode("001", "��һ�ڿ�");
		TreeNode TreeNode_lv1_1 = new TreeNode("0001", "��һ����");
		TreeNode TreeNode_lv1_1_1 = new TreeNode("00011", "����");
		TreeNode TreeNode_lv1_1_2 = new TreeNode("00012", "��ϰ");
		TreeNode TreeNode_lv1_1_2_1 = new TreeNode("000121", "��ϰ");
		TreeNode TreeNode_lv1_1_2_2 = new TreeNode("000122", "��ϰ");
		TreeNode TreeNode_lv1_1_2_3 = new TreeNode("000123", "��ϰ");
		TreeNode TreeNode_lv1_1_2_4 = new TreeNode("000124", "��ϰ");
		TreeNode TreeNode_lv1_1_2_5 = new TreeNode("000125", "��ϰ");
		TreeNode TreeNode_lv1_1_2_5_1 = new TreeNode("0001251", "��ϰ");
		TreeNode TreeNode_lv1_1_2_5_2 = new TreeNode("0001252", "��ϰ");
		TreeNode TreeNode_lv1_1_2_5_2_1 = new TreeNode("00012521", "��ϰ");
		TreeNode TreeNode_lv1_2 = new TreeNode("0002", "�ڶ�����");
		TreeNode TreeNode_lv1_3 = new TreeNode("0003", "��������");
		TreeNode TreeNode_lv1_3_1 = new TreeNode("00031", "ѡ����");
		TreeNode TreeNode_lv2 = new TreeNode("002", "�ڶ��ڿ�");
		TreeNode TreeNode_lv2_1 = new TreeNode("0021", "���Բ���");
		TreeNode TreeNode_lv2_2 = new TreeNode("0022", "��������");
		TreeNode TreeNode_lv3 = new TreeNode("003", "�����ڿ�");
		TreeNode TreeNode_lv3_1 = new TreeNode("0031", "���Բ���");
		TreeNode TreeNode_lv3_2 = new TreeNode("0032", "��������");
		TreeNode TreeNode_lv3_3 = new TreeNode("0033", "�ϻ�����");
		TreeNode TreeNode_lv3_4 = new TreeNode("0034", "���Կ���");
		TreeNode TreeNode_lv4 = new TreeNode("004", "���Ľڿ�");
		TreeNode TreeNode_lv4_1 = new TreeNode("0041", "���Բ���");
		TreeNode TreeNode_lv4_2 = new TreeNode("0042", "��������");
		TreeNode TreeNode_lv4_3 = new TreeNode("0043", "�ϻ�����");
		TreeNode TreeNode_lv4_4 = new TreeNode("0044", "���Կ���");
		TreeNode TreeNode_lv4_5 = new TreeNode("0045", "���Բ���");
		TreeNode TreeNode_lv4_6 = new TreeNode("0046", "��������");
		TreeNode TreeNode_lv4_7 = new TreeNode("0047", "�ϻ�����");
		TreeNode TreeNode_lv4_8 = new TreeNode("0048", "���Կ���");
		TreeNode TreeNode_lv5 = new TreeNode("005", "����ڿ�");
		TreeNode TreeNode_lv5_1 = new TreeNode("0051", "���Բ���");
		TreeNode TreeNode_lv5_2 = new TreeNode("0052", "��������");
		TreeNode TreeNode_lv5_3 = new TreeNode("0053", "�ϻ�����");
		TreeNode TreeNode_lv5_4 = new TreeNode("0054", "���Կ���");
		TreeNode TreeNode_lv5_5 = new TreeNode("0055", "���Կ���");
		TreeNode_lv1.add(TreeNode_lv1_1);
		TreeNode_lv1.add(TreeNode_lv1_2);
		TreeNode_lv1.add(TreeNode_lv1_3);
		root.add(TreeNode_lv1);
		TreeNode_lv1_1.add(TreeNode_lv1_1_1);
		TreeNode_lv1_1.add(TreeNode_lv1_1_2);
		TreeNode_lv2.add(TreeNode_lv2_1);
		TreeNode_lv2.add(TreeNode_lv2_2);
		root.add(TreeNode_lv2);
		TreeNode_lv3.add(TreeNode_lv3_1);
		TreeNode_lv3.add(TreeNode_lv3_2);
		TreeNode_lv3.add(TreeNode_lv3_3);
		TreeNode_lv3.add(TreeNode_lv3_4);
		root.add(TreeNode_lv3);

		TreeNode_lv4.add(TreeNode_lv4_1);
		TreeNode_lv4.add(TreeNode_lv4_2);
		TreeNode_lv4.add(TreeNode_lv4_3);
		TreeNode_lv4.add(TreeNode_lv4_4);
		TreeNode_lv4.add(TreeNode_lv4_5);
		TreeNode_lv4.add(TreeNode_lv4_6);
		TreeNode_lv4.add(TreeNode_lv4_7);
		TreeNode_lv4.add(TreeNode_lv4_8);
		root.add(TreeNode_lv4);

		TreeNode_lv5.add(TreeNode_lv5_1);
		TreeNode_lv5.add(TreeNode_lv5_2);
		TreeNode_lv5.add(TreeNode_lv5_3);
		TreeNode_lv5.add(TreeNode_lv5_4);
		root.add(TreeNode_lv5);

		TreeNode_lv1_1_2.add(TreeNode_lv1_1_2_1);
		TreeNode_lv1_1_2.add(TreeNode_lv1_1_2_2);
		TreeNode_lv1_1_2.add(TreeNode_lv1_1_2_3);
		TreeNode_lv1_1_2.add(TreeNode_lv1_1_2_4);
		TreeNode_lv1_1_2.add(TreeNode_lv1_1_2_5);
		TreeNode_lv1_1_2_5.add(TreeNode_lv1_1_2_5_1);
		TreeNode_lv1_1_2_5.add(TreeNode_lv1_1_2_5_2);
		TreeNode_lv1_1_2_5_2.add(TreeNode_lv1_1_2_5_2_1);
		TreeNode_lv1_3.add(TreeNode_lv1_3_1);
		nodes = new ArrayList<TreeNode>();
		nodes.add(root);
		nodes.add(TreeNode_lv5_5);
		treeAdapter = treeView.new TreeAdapter(nodes);
	}
}
