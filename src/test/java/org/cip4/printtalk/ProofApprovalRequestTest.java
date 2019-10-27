package org.cip4.printtalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.cip4.jdflib.core.KElement;
import org.cip4.printtalk.PrintTalk.EnumBusinessObject;
import org.junit.Test;

public class ProofApprovalRequestTest extends PrintTalkTestCase
{

	/**
	 *
	 *
	 */
	@Test
	public void testJobID()
	{
		final ProofApprovalRequest r = (ProofApprovalRequest) new PrintTalk().appendRequest(EnumBusinessObject.ProofApprovalRequest, null);
		r.setJobIDRef("j1");
		assertEquals("j1", r.getJobIDRef());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testProofItem()
	{
		final ProofApprovalRequest r = (ProofApprovalRequest) new PrintTalk().appendRequest(EnumBusinessObject.ProofApprovalRequest, null);
		r.setJobIDRef("j1");
		final KElement e = r.getCreateProofItem();
		assertNotNull(e);
		final KElement e2 = r.getProofItem();
		assertEquals(e2, e);
		reparse(r, false);
	}

}
