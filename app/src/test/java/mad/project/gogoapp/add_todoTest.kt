package mad.project.gogoapp

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class add_todoTest {

    private lateinit var add_todo: add_todo

    @Before
    fun setUp() {
        add_todo = add_todo()
    }

    @Test
    fun testAddTodo() {
        // Initialize add_todo before accessing it
        setUp()
        assertNotNull(add_todo)
    }
}
