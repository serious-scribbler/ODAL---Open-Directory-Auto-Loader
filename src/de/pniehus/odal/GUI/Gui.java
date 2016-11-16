package de.pniehus.odal.GUI;

import javax.swing.JFrame;

import java.awt.Rectangle;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;

import net.miginfocom.swing.MigLayout;

import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.FlowLayout;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.border.LineBorder;

public class Gui extends JFrame{
	private JTextField urlField;
	public Gui() {
		setTitle("ODAL - Open Direcory Auto Loader");
		setSize(new Dimension(1280, 720));
		
		JPanel TopBar = new JPanel();
		
		JPanel DirectoryPanel = new JPanel();
		DirectoryPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel FilterPanel = new JPanel();
		FilterPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel TaskPanel = new JPanel();
		TaskPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(DirectoryPanel, GroupLayout.PREFERRED_SIZE, 592, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(FilterPanel, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(TaskPanel, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE))
						.addComponent(TopBar, GroupLayout.PREFERRED_SIZE, 1263, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(TopBar, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(DirectoryPanel, GroupLayout.PREFERRED_SIZE, 553, Short.MAX_VALUE)
						.addComponent(FilterPanel, GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
						.addComponent(TaskPanel, GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))
					.addContainerGap())
		);
		GroupLayout gl_FilterPanel = new GroupLayout(FilterPanel);
		gl_FilterPanel.setHorizontalGroup(
			gl_FilterPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 322, Short.MAX_VALUE)
		);
		gl_FilterPanel.setVerticalGroup(
			gl_FilterPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 564, Short.MAX_VALUE)
		);
		FilterPanel.setLayout(gl_FilterPanel);
		
		JLabel selectorLabel = new JLabel("Selector");
		selectorLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JList list = new JList();
		
		JButton removeThisOpenDirectory = new JButton("Remove this open directory");
		
		JLabel lblSelectADirectory = new JLabel("Select a directory:");
		lblSelectADirectory.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JPanel replaceWithTree = new JPanel();
		replaceWithTree.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JButton btnSelectAll = new JButton("Select all");
		
		JButton btnDeselectAll = new JButton("Deselect all");
		GroupLayout gl_DirectoryPanel = new GroupLayout(DirectoryPanel);
		gl_DirectoryPanel.setHorizontalGroup(
			gl_DirectoryPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_DirectoryPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_DirectoryPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(replaceWithTree, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
						.addComponent(list, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
						.addGroup(gl_DirectoryPanel.createSequentialGroup()
							.addComponent(lblSelectADirectory)
							.addPreferredGap(ComponentPlacement.RELATED, 304, Short.MAX_VALUE)
							.addComponent(removeThisOpenDirectory))
						.addGroup(gl_DirectoryPanel.createSequentialGroup()
							.addComponent(btnSelectAll, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDeselectAll, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
				.addGroup(gl_DirectoryPanel.createSequentialGroup()
					.addContainerGap(281, Short.MAX_VALUE)
					.addComponent(selectorLabel)
					.addGap(255))
		);
		gl_DirectoryPanel.setVerticalGroup(
			gl_DirectoryPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_DirectoryPanel.createSequentialGroup()
					.addComponent(selectorLabel)
					.addGap(13)
					.addGroup(gl_DirectoryPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(removeThisOpenDirectory)
						.addComponent(lblSelectADirectory))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(replaceWithTree, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_DirectoryPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSelectAll)
						.addComponent(btnDeselectAll, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		DirectoryPanel.setLayout(gl_DirectoryPanel);
		
		JPanel LoadPanel = new JPanel();
		LoadPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel SimultaneousTasksPanel = new JPanel();
		SimultaneousTasksPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GroupLayout gl_TopBar = new GroupLayout(TopBar);
		gl_TopBar.setHorizontalGroup(
			gl_TopBar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_TopBar.createSequentialGroup()
					.addContainerGap()
					.addComponent(LoadPanel, GroupLayout.PREFERRED_SIZE, 919, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(SimultaneousTasksPanel, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ButtonPanel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_TopBar.setVerticalGroup(
			gl_TopBar.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_TopBar.createSequentialGroup()
					.addContainerGap(14, Short.MAX_VALUE)
					.addGroup(gl_TopBar.createParallelGroup(Alignment.TRAILING)
						.addComponent(ButtonPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(SimultaneousTasksPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(LoadPanel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
					.addGap(3))
		);
		
		JLabel addDirectoryLabel = new JLabel("Add directory");
		addDirectoryLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel urlLabel = new JLabel("URL:");
		urlLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		urlField = new JTextField();
		urlField.setColumns(10);
		
		JCheckBox includeSubdirectories = new JCheckBox("Include subdirectories");
		
		JCheckBox fetchFileSize = new JCheckBox("Fetch file size");
		
		JButton btnFetch = new JButton("Fetch");
		GroupLayout gl_LoadPanel = new GroupLayout(LoadPanel);
		gl_LoadPanel.setHorizontalGroup(
			gl_LoadPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_LoadPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_LoadPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(addDirectoryLabel)
						.addGroup(gl_LoadPanel.createSequentialGroup()
							.addComponent(urlLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(urlField, GroupLayout.PREFERRED_SIZE, 487, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
					.addGroup(gl_LoadPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_LoadPanel.createSequentialGroup()
							.addComponent(includeSubdirectories)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(fetchFileSize))
						.addComponent(btnFetch, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(128, Short.MAX_VALUE))
		);
		gl_LoadPanel.setVerticalGroup(
			gl_LoadPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_LoadPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_LoadPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(addDirectoryLabel)
						.addComponent(includeSubdirectories)
						.addComponent(fetchFileSize))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_LoadPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(urlLabel)
						.addComponent(urlField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnFetch, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(39, Short.MAX_VALUE))
		);
		LoadPanel.setLayout(gl_LoadPanel);
		
		JSpinner spinner = new JSpinner();
		
		JLabel simultaneousLabel = new JLabel("Max. simultaneous Downloads:");
		GroupLayout gl_SimultaneousTasksPanel = new GroupLayout(SimultaneousTasksPanel);
		gl_SimultaneousTasksPanel.setHorizontalGroup(
			gl_SimultaneousTasksPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_SimultaneousTasksPanel.createSequentialGroup()
					.addContainerGap(59, Short.MAX_VALUE)
					.addComponent(simultaneousLabel)
					.addGap(57))
				.addGroup(gl_SimultaneousTasksPanel.createSequentialGroup()
					.addGap(108)
					.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(111, Short.MAX_VALUE))
		);
		gl_SimultaneousTasksPanel.setVerticalGroup(
			gl_SimultaneousTasksPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_SimultaneousTasksPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(simultaneousLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinner, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
					.addGap(20))
		);
		SimultaneousTasksPanel.setLayout(gl_SimultaneousTasksPanel);
		TopBar.setLayout(gl_TopBar);
		getContentPane().setLayout(groupLayout);
	}
}
