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

public class Gui extends JFrame{
	private JTextField urlField;
	public Gui() {
		setTitle("ODAL - Open Direcory Auto Loader");
		setSize(new Dimension(1280, 720));
		
		JPanel TopBar = new JPanel();
		
		JPanel DirectoryPanel = new JPanel();
		
		JPanel FilterPanel = new JPanel();
		
		JPanel TaskPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
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
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(DirectoryPanel, GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(FilterPanel, GroupLayout.PREFERRED_SIZE, 564, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(TaskPanel, GroupLayout.PREFERRED_SIZE, 564, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		
		JLabel selectorLabel = new JLabel("Selector");
		selectorLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JList list = new JList();
		
		JButton removeThisOpenDirectory = new JButton("Remove this open directory");
		
		JLabel lblSelectADirectory = new JLabel("Select a directory:");
		lblSelectADirectory.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JPanel replaceWithTree = new JPanel();
		
		JButton btnSelectAll = new JButton("Select all");
		
		JButton btnDeselectAll = new JButton("Deselect all");
		GroupLayout gl_DirectoryPanel = new GroupLayout(DirectoryPanel);
		gl_DirectoryPanel.setHorizontalGroup(
			gl_DirectoryPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_DirectoryPanel.createSequentialGroup()
					.addGroup(gl_DirectoryPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_DirectoryPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(replaceWithTree, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE))
						.addGroup(gl_DirectoryPanel.createSequentialGroup()
							.addGap(273)
							.addComponent(selectorLabel))
						.addGroup(gl_DirectoryPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(list, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE))
						.addGroup(gl_DirectoryPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblSelectADirectory)
							.addPreferredGap(ComponentPlacement.RELATED, 304, Short.MAX_VALUE)
							.addComponent(removeThisOpenDirectory))
						.addGroup(Alignment.LEADING, gl_DirectoryPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnSelectAll, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDeselectAll, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_DirectoryPanel.setVerticalGroup(
			gl_DirectoryPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_DirectoryPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_DirectoryPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_DirectoryPanel.createSequentialGroup()
							.addComponent(selectorLabel)
							.addGap(31))
						.addGroup(gl_DirectoryPanel.createSequentialGroup()
							.addGroup(gl_DirectoryPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(removeThisOpenDirectory)
								.addComponent(lblSelectADirectory))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(replaceWithTree, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_DirectoryPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnDeselectAll, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
						.addComponent(btnSelectAll, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
					.addContainerGap())
		);
		DirectoryPanel.setLayout(gl_DirectoryPanel);
		
		JPanel LoadPanel = new JPanel();
		
		JPanel SimultaneousTasksPanel = new JPanel();
		
		JPanel ButtonPanel = new JPanel();
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
					.addContainerGap(61, Short.MAX_VALUE)
					.addGroup(gl_SimultaneousTasksPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_SimultaneousTasksPanel.createSequentialGroup()
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(122))
						.addGroup(Alignment.TRAILING, gl_SimultaneousTasksPanel.createSequentialGroup()
							.addComponent(simultaneousLabel)
							.addGap(57))))
		);
		gl_SimultaneousTasksPanel.setVerticalGroup(
			gl_SimultaneousTasksPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_SimultaneousTasksPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(simultaneousLabel)
					.addGap(15)
					.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(38, Short.MAX_VALUE))
		);
		SimultaneousTasksPanel.setLayout(gl_SimultaneousTasksPanel);
		TopBar.setLayout(gl_TopBar);
		getContentPane().setLayout(groupLayout);
	}
}
