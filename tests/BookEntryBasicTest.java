import org.junit.Test;
import static org.junit.Assert.*;

public class BookEntryBasicTest extends BookEntryTest {

    // ------------------------- check fields --------------------

    @Test
    public void testFieldTypes() {
        for (int i = 0; i < BOOK_ENTRY_FIELD_NAMES.length; i++) {
            FieldTestUtils.checkFieldType(testBook, BOOK_ENTRY_FIELD_TYPES[i], BOOK_ENTRY_FIELD_NAMES[i]);
        }
    }

    // ------------------------- check constructor --------------------

    @Test
    public void testCtorFieldInitialisation() {
        BookEntryTestUtils.checkBookFieldValues(testBook, BOOK_ENTRY_FIELD_NAMES, BOOK_ENTRY_FIELD_VALUES);
    }

    // ------------------------- check getters --------------------

    @Test
    public void testGetTitle() {
        String fieldName = TITLE_FIELD_NAME;
        String expected = "Test Title";
        FieldTestUtils.setPrivateField(testBook, testBook.getClass(), fieldName, expected);

        String actual = testBook.getTitle();
        assertEquals("Unexpected " + fieldName + " returned by getter.", expected, actual);
    }

    @Test
    public void testGetAuthors() {
        String fieldName = AUTHORS_FIELD_NAME;
        String[] expected = { "Test Author A", "Test Author B" };
        FieldTestUtils.setPrivateField(testBook, testBook.getClass(), fieldName, expected);

        String[] actual = testBook.getAuthors();
        assertArrayEquals("Unexpected " + fieldName + " returned by getter.", expected, actual);
    }

    @Test
    public void testGetRating() {
        String fieldName = RATING_FIELD_NAME;
        float expected = 2.3f;
        FieldTestUtils.setPrivateField(testBook, testBook.getClass(), fieldName, expected);

        float actual = testBook.getRating();
        assertEquals("Unexpected " + fieldName + " returned by getter.", expected, actual, CMP_DELTA);
    }

    @Test
    public void testGetISBN() {
        String fieldName = ISBN_FIELD_NAME;
        String expected = "158234681X";
        FieldTestUtils.setPrivateField(testBook, testBook.getClass(), fieldName, expected);

        String actual = testBook.getISBN();
        assertEquals("Unexpected " + fieldName + " returned by getter.", expected, actual);
    }

    @Test
    public void testGetPages() {
        String fieldName = PAGES_FIELD_NAME;
        int expected = 123;
        FieldTestUtils.setPrivateField(testBook, testBook.getClass(), fieldName, expected);

        int actual = testBook.getPages();
        assertEquals("Unexpected " + fieldName + " returned by getter.", expected, actual);
    }

    // ------------------------- check error handling of constructor ---

    @Test(expected = NullPointerException.class)
    public void testTitleNullError() {
        new BookEntry(null, DEFAULT_AUTHORS, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyTitleError() {
        String title = "";
        new BookEntry(title, DEFAULT_AUTHORS, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);
    }

    @Test(expected = NullPointerException.class)
    public void testAuthorsNullError() {
        new BookEntry(DEFAULT_TITLE, null, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);
    }

    @Test(expected = NullPointerException.class)
    public void testAuthorsInstanceNullError() {
        String[] authors = {"Harry Klein", "Angela Merkel", null};
        new BookEntry(DEFAULT_TITLE, authors, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAuthorsEmptyError() {
        String[] authors = {""};
        new BookEntry(DEFAULT_TITLE, authors, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRatingBoundError() {
        float rating = (float) -1.0;
        new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, rating, DEFAULT_ISBN, DEFAULT_PAGES);
    }

    @Test(expected = NullPointerException.class)
    public void testISBNNullError() {
        new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, DEFAULT_RATING, null, DEFAULT_PAGES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testISBNEmptyError() {
        String ISBN = "";
        new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, DEFAULT_RATING, ISBN, DEFAULT_PAGES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPagesBoundError() {
        int pages = -1;
        new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, DEFAULT_RATING, DEFAULT_ISBN, pages);
    }


    // ------------------------- check equals and hash code --------

    private void checkEquality(BookEntry bookA, BookEntry bookB, String field, boolean expected) {
        if (expected) {
            assertTrue("True return value expected for same fields.", bookA.equals(bookB) && bookB.equals(bookA));
            assertEquals("Hashcode expected to be the same for objects with the same state.", bookA.hashCode(), bookB.hashCode());
        } else {
            assertTrue("False return value expected for different " + field + ".", !bookA.equals(bookB) && !bookB.equals(bookA));
            assertNotEquals("Hashcode should be different for objects with different state.", bookA.hashCode(), bookB.hashCode());
        }
    }

    @Test
    public void testEqualsAndHashCode() {
        BookEntry bookA = new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);
        BookEntry bookB = new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);

        assertTrue("True return value expected for same book instance.", bookA.equals(bookA) && bookB.equals(bookB));
        assertEquals("Hashcode expected to be the same for same instance.", bookA.hashCode(), bookA.hashCode());

        assertNotEquals("False return value expected if compared to different object type.", "test", bookA);
        assertNotEquals("False return value expected if compared to null.", null, bookA);

        checkEquality(bookA, bookB, TITLE_FIELD_NAME, true);

        bookA = new BookEntry("Title A", DEFAULT_AUTHORS, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);
        bookB = new BookEntry("Title B", DEFAULT_AUTHORS, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);

        checkEquality(bookA, bookB, TITLE_FIELD_NAME, false);
        
        bookA = new BookEntry(DEFAULT_TITLE, new String[]{"Author A"}, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);
        bookB = new BookEntry(DEFAULT_TITLE, new String[]{"Author B"}, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);

        checkEquality(bookA, bookB, AUTHORS_FIELD_NAME, false);

        bookA = new BookEntry(DEFAULT_TITLE, new String[]{"Author A"}, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);
        bookB = new BookEntry(DEFAULT_TITLE, new String[]{"Author A", "Author B"}, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);

        checkEquality(bookA, bookB, AUTHORS_FIELD_NAME, false);
    
        bookA = new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, 2f, DEFAULT_ISBN, DEFAULT_PAGES);
        bookB = new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, 3f, DEFAULT_ISBN, DEFAULT_PAGES);

        checkEquality(bookA, bookB, RATING_FIELD_NAME, false);

        bookA = new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, DEFAULT_RATING, "1400054036", DEFAULT_PAGES);
        bookB = new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, DEFAULT_RATING, "439554896", DEFAULT_PAGES);

        checkEquality(bookA, bookB, ISBN_FIELD_NAME, false);

        bookA = new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, DEFAULT_RATING, DEFAULT_ISBN, 300);
        bookB = new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, DEFAULT_RATING, DEFAULT_ISBN, 400);

        checkEquality(bookA, bookB, PAGES_FIELD_NAME, false);
    }

    // ------------------------- check toString --------------------

    @Test
    public void testToStringReturnValue() {
        String actualResult = testBook.toString();

        // ignore leading and trailing white spaces
        // and correct for potential Windows line endings
        assertEquals("ToString result not as expected.", DEFAULT_TOSTRING_RESULT.replaceAll("\r", "").trim(),
                actualResult.replaceAll("\r", "").trim());
    }
}
