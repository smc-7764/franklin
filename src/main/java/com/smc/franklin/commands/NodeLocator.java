package com.smc.franklin.commands;

import java.util.List;

import com.smc.franklin.dao.Entry;
import com.smc.franklin.dao.Requirement;

public class NodeLocator {

	public Located locate(Requirement plan, String nodeId) {
		
		Located located = locate(plan.getEntries(), nodeId);
		
		Entry prev = null; 
		for ( Entry entry : located.getNeighborhood()) {
			if ( prev == null ) { prev = entry; continue;}
			
			if ( entry.getId().equals(nodeId)) {
				located.setUp(prev);
				located.setEntry(entry);
				continue;
			}
			if ( located.getEntry() != null) {
				located.setDown(entry); break;
			}
			//else
			prev = entry;
		}
		//wife is playing mario through back roads. hard to troubleshoot this one right now
		//seems that a parent that does not have a parent may need to be retrieved as a special, "subsequent" retrieval
		if (located.getUp() == null && located.getEntry().getLevel() != null && located.getEntry().getSection() != null) {
			if (located.getEntry().getLevel() == 1 & located.getEntry().getSection() > 1) {
				/*
				 * looks like a top level node that wants to tuck under a
				 * preceeding top level node
				 */
				int preceeder = located.getEntry().getSection() - 1;
				for (Entry entry : plan.getEntries()) {
					if (entry.getSection().equals(preceeder)) {
						located.setUp(entry);
						break;
					}
				}
			}
		}
		return located;
	}
	
	private Located locate(List<Entry> entries, String nodeId) {
		Located located = null;
		for ( Entry entry: entries) {
			if ( entry.getId().equals(nodeId)) {
				located = new Located();
				located.entry = entry;
				located.neighborhood = entries;
				break;
			} else {
				located = locate(entry.getChildNodes(), nodeId);
				if ( located != null ) {
					if ( located.parent == null ) {
						located.parent = entry;
					}
					break;
				}
			}
		}
		return located;
	}
	
	static class Located {
		private Entry up;
		private Entry down;
		private Entry entry;
		private Entry parent;
		private List<Entry> neighborhood;
		/**
		 * @return the entry
		 */
		public Entry getEntry() {
			return entry;
		}
		/**
		 * @param entry the entry to set
		 */
		public void setEntry(Entry entry) {
			this.entry = entry;
		}
		/**
		 * @return the parent
		 */
		public Entry getParent() {
			return parent;
		}
		/**
		 * @param parent the parent to set
		 */
		public void setParent(Entry parent) {
			this.parent = parent;
		}
		/**
		 * @return the neighborhood
		 */
		public List<Entry> getNeighborhood() {
			return neighborhood;
		}
		/**
		 * @param neighborhood the neighborhood to set
		 */
		public void setNeighborhood(List<Entry> neighborhood) {
			this.neighborhood = neighborhood;
		}
		/**
		 * @return the up
		 */
		public Entry getUp() {
			return up;
		}
		/**
		 * @param up the up to set
		 */
		public void setUp(Entry up) {
			this.up = up;
		}
		/**
		 * @return the down
		 */
		public Entry getDown() {
			return down;
		}
		/**
		 * @param down the down to set
		 */
		public void setDown(Entry down) {
			this.down = down;
		}
	}
}
