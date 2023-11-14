package repository;

import model.AudioBook;
import model.EBook;
import model.builder.AudioBookBuilder;
import model.builder.EBookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AudioBookRepositoryMySQL  implements BookRepository<AudioBook> {
    private final Connection connection;

    public AudioBookRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<AudioBook> findAll() {
        String sql = "SELECT * FROM book;";

        List<AudioBook> books = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                books.add(getBookFromResultSet(resultSet));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }


        return books;
    }

    @Override
    public Optional<AudioBook> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?;";


        try{

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);


            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                AudioBook book = getBookFromResultSet(resultSet);
                return Optional.of(book);
            }

        } catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }


        return Optional.empty();
    }

    /**
     *
     * How to reproduce a sql injection attack on insert statement
     *
     *
     * 1) Uncomment the lines below and comment out the PreparedStatement part
     * 2) For the Insert Statement DROP TABLE SQL Injection attack to succeed we will need multi query support to be added to our connection
     * Add to JDBConnectionWrapper the following flag after the DB_URL + schema concatenation: + "?allowMultiQueries=true"
     * 3) book.setAuthor("', '', null); DROP TABLE book; -- "); // this will delete the table book
     * 3*) book.setAuthor("', '', null); SET FOREIGN_KEY_CHECKS = 0; SET GROUP_CONCAT_MAX_LEN=32768; SET @tables = NULL; SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables FROM information_schema.tables WHERE table_schema = (SELECT DATABASE()); SELECT IFNULL(@tables,'dummy') INTO @tables; SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables); PREPARE stmt FROM @tables; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET FOREIGN_KEY_CHECKS = 1; --"); // this will delete all tables. You are not required to know the table name anymore.
     * 4) Run the program. You will get an exception on findAll() method because the test_library.book table does not exist anymore
     */


    // ALWAYS use PreparedStatement when USER INPUT DATA is present
    // DON'T CONCATENATE Strings!

    @Override
    public boolean save(AudioBook book) {
        String sql = "INSERT INTO book VALUES(null, ?, ?, ?, ?, ?);";

        String newSql = "INSERT INTO book VALUES(null, \'" + book.getAuthor() +"\', \'"+ book.getTitle()+"\', null );";


        try{
            //Statement statement = connection.createStatement();
            //statement.executeUpdate(newSql);
            //return true;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.setInt(5, book.getRunTime());


            int rowsInserted = preparedStatement.executeUpdate();

            return (rowsInserted != 1) ? false : true;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeAll() {
        String sql = "TRUNCATE TABLE book;";


        try{

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private AudioBook getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new AudioBookBuilder()
                .setId(resultSet.getLong("id"))
                .setAuthor(resultSet.getString("author"))
                .setTitle(resultSet.getString("title"))
                .setPublishedDate(new java.sql.Date((resultSet.getDate("publishedDate")).getTime()).toLocalDate())
                .asAudioBook()
                .setRunTime(Integer.parseInt(resultSet.getString("runTime")))
                .build();
    }
}
