package com.std.framework.widget.tree;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.std.framework.R;

/**
 * 
 * �� �� �����Ϳؼ�
 * �������� : 2013-10-31
 * �� �� �� lx
 * �޸����� :
 * �� �� �� ��
 * 
 * @version : 1.0
 */
public class TreeView extends ListView {
	private List<TreeNode> mNodes;
	private TreeAdapter mAdapter;
	private Context mContext;
	// չ���ڵ�㼶(0:ȫ��չ��)
	private int mExpandLevel = 1;
	// ͬ���ڵ�ÿ��ֻչ��һ���ڵ�
	private boolean isSingleOfLevel = true;

	// �ڵ�������
	private int mOffset = 30;
	private int mTop = 5;
	private int mRight = 5;
	private int mBottom = 5;

	// �ڵ�չ��ʱ��ʾ��ͼƬ
	private int expanderIcon = -1;
	// �ڵ��۵�ʱ��ʾ��ͼƬ
	private int collsapsedIcon = -1;
	// Ҷ�ӽڵ���ʾ��ͼƬ
	private int leafIco = -1;

	public TreeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	public TreeView(Context context) {
		super(context);
		mContext = context;
	}

	public void setNodes(List<TreeNode> nodes) {
		mNodes = nodes;
	}

	/**
	 * 
	 * �� �� ���������ڵ�������
	 * �������� : 2013-10-30
	 * �� �� �� lx
	 * �޸����� :
	 * �� �� �� ��
	 * 
	 * @version : 1.0
	 * @param dis
	 * 
	 */
	public void setOffset(int dis) {
		mOffset = dis;
	}

	/**
	 * 
	 * �� �� ��չ�����ڵ�ǰ���۵����ýڵ�ͬ�����������
	 * �������� : 2013-11-7
	 * �� �� �� lx
	 * �޸����� :
	 * �� �� �� ��
	 * 
	 * @version : 1.0
	 * @param collapse��true���۵���false:���۵�
	 * 
	 */
	public void collapseOthersBeforeExpander(boolean collapse) {
		isSingleOfLevel = collapse;
	}

	// �ϱ߾�
	private void setNodeTop(int top) {
		mTop = top;
	}

	// �ұ߾�
	private void setNodeRight(int right) {
		mRight = right;
	}

	// �ױ߾�
	private void setNodeBottom(int bottom) {
		mBottom = bottom;
	}

	/**
	 * 
	 * �� �� �����ýڵ�չ��ʱ��ʾ��ͼƬID
	 * �������� : 2013-10-30
	 * �� �� �� lx
	 * �޸����� :
	 * �� �� �� ��
	 * 
	 * @version : 1.0
	 * @param id
	 * 
	 */
	public void setExpanderImageResourceId(int id) {
		expanderIcon = id;
	}

	/**
	 * 
	 * �� �� �����ýڵ��۵�ʱ��ʾ��ͼƬID
	 * �������� : 2013-10-30
	 * �� �� �� lx
	 * �޸����� :
	 * �� �� �� ��
	 * 
	 * @version : 1.0
	 * @param id
	 * 
	 */
	public void setCollsapsedImageResourceId(int id) {
		collsapsedIcon = id;
	}

	/**
	 * 
	 * �� �� ������Ҷ�ӽڵ���ʾ��ͼƬID
	 * �������� : 2013-10-30
	 * �� �� �� lx
	 * �޸����� :
	 * �� �� �� ��
	 * 
	 * @version : 1.0
	 * @param id
	 * 
	 */
	public void setLeafImageResourceId(int id) {
		leafIco = id;
	}

	public void setExpandLevel(int level) {
		mExpandLevel = level;
	}

	public void setAdapter(TreeAdapter adapter) {
		mAdapter = adapter;
		super.setAdapter(adapter);
	}

	@Override
	public boolean performItemClick(View view, int position, long id) {
		// TODO Auto-generated method stub
		((TreeAdapter) getAdapter()).ExpandOrCollapse(view, position);
		return super.performItemClick(view, position, id);
	}

	public class TreeAdapter extends BaseAdapter {
		private List<TreeNode> nodes;
		private List<TreeNode> nodesCache;
		private LayoutInflater layoutInflater;

		public TreeAdapter(List<TreeNode> treeNodes) {
			nodes = new ArrayList<TreeNode>();
			nodesCache = new ArrayList<TreeNode>();
			this.layoutInflater = (LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			initTree(treeNodes);
			ExpanderLevel(mExpandLevel);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return nodes.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return nodes.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.treenode, null);
				holder = new ViewHolder();
				holder.stateImageView = (ImageView) convertView.findViewById(R.id.stateimg);
				holder.value = (TextView) convertView.findViewById(R.id.value);
				holder.position = position;
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			// ���ڵ��ֵ��������
			TreeNode node = nodes.get(position);
			if (node != null) {
				holder.value.setText(node.getValue());
				if (!node.isLeaf()) {
					holder.stateImageView.setVisibility(View.VISIBLE);
					if (node.isExpanded) {
						if (expanderIcon == -1)
							holder.stateImageView.setImageResource(R.drawable.downarrow);
						else
							holder.stateImageView.setImageResource(expanderIcon);
					}
					else {
						if (collsapsedIcon == -1)
							holder.stateImageView.setImageResource(R.drawable.rightarrow);
						else
							holder.stateImageView.setImageResource(collsapsedIcon);
					}
				}
				else {
					if (leafIco == -1)
						holder.stateImageView.setVisibility(View.GONE);
					else
						holder.stateImageView.setImageResource(leafIco);
				}
			}
			convertView.setPadding(mOffset * node.getLevel(), mTop, mRight, mBottom);
			return convertView;
		}

		private void initTree(List<TreeNode> node) {
			for (int i = 0; i < node.size(); i++) {
				if (node.get(i).isLeaf()) {
					nodes.add(node.get(i));
					nodesCache.add(node.get(i));
				}
				else
					cascadeAdd(node.get(i));
			}

		}

		// ���û����ĳ��LIST��ʱ�� ���ƽڵ�����
		public void ExpandOrCollapse(View view, int position) {
			TreeNode n = nodes.get(position);
			Log.i("LX", n.value + " isExpanded:" + n.isExpanded());
			if (n != null) {
				n.setExpanded(!n.isExpanded);
				if (!n.isLeaf()) {
					if (n.isExpanded) {
						List<TreeNode> temp = new ArrayList<TreeNode>();
						cascadeExpand(n, temp);
						for (int i = 0; i < temp.size(); i++) {
							nodes.add(position + i + 1, temp.get(i));
						}
						if (isSingleOfLevel)
							CollapseOtherNodesOfSameLevel(n, n.getLevel());
					}
					else {
						cascadeCollapse(n);
					}
					this.notifyDataSetChanged();
				}
			}
		}

		// �۵���ָ�������ͬ���������ڵ�
		private void CollapseOtherNodesOfSameLevel(TreeNode node, int level) {
			for (int i = 0; i < nodesCache.size(); i++) {
				TreeNode s_node = nodesCache.get(i);
				if (nodes.contains(s_node) && s_node.getLevel() == level && s_node.isExpanded && !node.getId().equals(s_node.getId())) {
					cascadeCollapse(s_node);
					s_node.setExpanded(false);
				}
			}
			notifyDataSetChanged();
		}

		// ���ô�ʱչ������
		private void ExpanderLevel(int level) {
			if (level == 0)
				return;
			nodes.clear();
			for (int i = 0; i < nodesCache.size(); i++) {
				// �õ�ÿһ���ڵ�
				TreeNode n = nodesCache.get(i);
				if (n.getLevel() <= level) {
					if (n.getLevel() < level) {
						n.setExpanded(true);
					}
					else {
						n.setExpanded(false);
					}
					nodes.add(n);
				}
				else {
					n.setExpanded(false);
				}
			}
			notifyDataSetChanged();
		}

		// ��������ӽڵ�
		private void cascadeAdd(TreeNode node) {
			nodes.add(node);
			nodesCache.add(node);
			for (int i = 0; i < node.getChildren().size(); i++) {
				cascadeAdd(node.getChildren().get(i));
			}
		}

		// ����չ���ӽڵ�
		private void cascadeExpand(TreeNode node, List<TreeNode> refNodes) {
			for (int i = 0; i < node.children.size(); i++) {
				TreeNode n = node.children.get(i);
				refNodes.add(n);
				if (n.isExpanded)
					cascadeExpand(n, refNodes);
			}
		}

		// �����۵��ӽڵ�
		private void cascadeCollapse(TreeNode node) {
			for (TreeNode n : node.children) {
				if (nodes.contains(n)) {
					nodes.remove(n);
					cascadeCollapse(n);
				}
			}
		}

		public class ViewHolder {
			ImageView stateImageView;
			TextView value;
			int position;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

}
