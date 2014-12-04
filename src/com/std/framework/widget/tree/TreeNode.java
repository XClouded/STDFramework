package com.std.framework.widget.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	public String id;// �ڵ�id
	public String value;// �ڵ���ʾ������
	public TreeNode parent;// ���ڵ�
	public List<TreeNode> children = new ArrayList<TreeNode>();// �ӽڵ�
	public boolean isExpanded = true;// �Ƿ���չ��״̬
	public boolean isSelected = false;
	public boolean isShowCheckBox = false;
	public int ico = -1;// ͼ��
	public int stateImg = -1;

	public TreeNode(String id, String value) {
		this.id = id;
		this.value = value;
	}

	/**
	 * ��ø��ڵ�
	 * 
	 * @return
	 */
	public TreeNode getParent() {
		return this.parent;
	}

	/**
	 * ���ýڵ�ֵ
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * ��ýڵ�ֵ
	 * 
	 * @return
	 */
	public String getValue() {
		return this.value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isShowCheckBox() {
		return isShowCheckBox;
	}

	public void setShowCheckBox(boolean isShowCheckBox) {
		this.isShowCheckBox = isShowCheckBox;
	}

	public int getIco() {
		return ico;
	}

	public void setIco(int ico) {
		this.ico = ico;
	}

	/**
	 * �Ƿ���ڵ�
	 * 
	 * @return
	 */
	public boolean isRoot() {
		return parent == null ? true : false;
	}

	/**
	 * ����ӽڵ�
	 * 
	 * @return
	 */
	public List<TreeNode> getChildren() {
		return this.children;
	}

	/**
	 * ����ӽڵ�
	 * 
	 * @param node
	 */
	public void add(TreeNode node) {
		if (!children.contains(node)) {
			node.parent = this;
			children.add(node);
		}
	}

	/**
	 * ��������ӽڵ�
	 */
	public void clear() {
		children.clear();
	}

	/**
	 * ɾ��һ���ӽڵ�
	 * 
	 * @param node
	 */
	public void remove(TreeNode node) {
		if (!children.contains(node)) {
			children.remove(node);
		}
	}

	/**
	 * ɾ��ָ��λ�õ��ӽڵ�
	 * 
	 * @param location
	 */
	public void remove(int location) {
		children.remove(location);
	}

	/**
	 * ��ýڵ�ļ���,���ڵ�Ϊ0
	 * 
	 * @return
	 */
	public int getLevel() {
		return parent == null ? 1 : parent.getLevel() + 1;
	}

	/**
	 * �Ƿ�Ҷ�ڵ�,��û���ӽڵ�Ľڵ�
	 * 
	 * @return
	 */
	public boolean isLeaf() {
		return children.size() < 1 ? true : false;
	}

	/**
	 * ��ǰ�ڵ��Ƿ���չ��״̬
	 * 
	 * @return
	 */
	public boolean isExpanded() {
		return isExpanded;
	}

	/**
	 * ���ýڵ�չ��״̬
	 * 
	 * @return
	 */
	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

}