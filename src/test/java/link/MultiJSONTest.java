package link;

import org.junit.Test;

import com.bunker.bkframework.clientapi.link.MultiJSONLink;
import com.bunker.bkframework.clientapi.link.WorkingParamLink;

public class MultiJSONTest {

	@Test public void test() {
		MultiJSONLink link = new MultiJSONLink();
		link.addChain(new WorkingParamLink(new TestLink())
				.addWorkingParam("a", "b", "C")
				.addWorkingParam("A", "b", "c"), "a");
		System.out.println(link.getJson());
	}
}