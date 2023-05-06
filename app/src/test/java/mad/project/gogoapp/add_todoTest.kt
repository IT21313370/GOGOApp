package mad.project.gogoapp
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class add_todoTest {

    private lateinit var add_todo: add_todo

    @Before
    fun setUp() {
        add_todo = add_todo()
    }

    @Test
    fun testAddTodo() {
        assertNotNull(add_todo)
    }
}
