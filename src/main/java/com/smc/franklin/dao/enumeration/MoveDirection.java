package com.smc.franklin.dao.enumeration;

/**
 * Describes the direction to move a node
 * @author sean_
 *
 */
public enum MoveDirection {
	/**Up one in the hierarchy */
	PROMOTE,
	/**Down one in the hierarchy */
	DEMOTE,
	/**Up one - same place in the hierarchy */
	UP,
	/**Down one - same place in the hierarchy */
	DOWN;
}
