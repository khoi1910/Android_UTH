package com.example.btvn01t04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// Data Models (OOP Classes)
data class Book(
    val id: String,
    val title: String,
    val author: String,
    val description: String,
    var isAvailable: Boolean = true,
    var borrowedBy: String? = null
)

data class Student(
    val id: String,
    var name: String,
    val email: String,
    val borrowedBooks: MutableList<String> = mutableListOf()
)

// Repository Class for Data Management
class LibraryRepository {
    private val _books = mutableListOf<Book>()
    private val _students = mutableListOf<Student>()
    private var _currentStudentName: String = "Sinh Vien Moi"

    val books: List<Book> get() = _books.toList()
    val students: List<Student> get() = _students.toList()
    val currentStudentName: String get() = _currentStudentName

    init {
        // Sample data
        addSampleData()
    }

    private fun addSampleData() {
        _books.addAll(listOf(
            Book("1", "Lập trình Android", "Nguyễn Văn A", "Sách học lập trình Android cơ bản"),
            Book("2", "Kotlin Programming", "John Doe", "Hướng dẫn lập trình Kotlin từ cơ bản đến nâng cao"),
            Book("3", "Jetpack Compose", "Jane Smith", "Xây dựng UI hiện đại với Jetpack Compose"),
            Book("4", "Mobile Development", "Trần Văn B", "Phát triển ứng dụng di động hiện đại"),
            Book("5", "UI/UX Design", "Lê Thị C", "Thiết kế giao diện người dùng chuyên nghiệp")
        ))
    }

    fun addBook(book: Book) {
        _books.add(book)
    }

    fun setCurrentStudentName(name: String) {
        _currentStudentName = name
    }

    fun borrowBooks(bookIds: Set<String>): Student? {
        if (bookIds.isEmpty() || _currentStudentName.isBlank()) return null

        // Tìm xem sinh viên đã tồn tại chưa
        var existingStudent = _students.find { it.name == _currentStudentName }

        if (existingStudent == null) {
            // Tạo sinh viên mới nếu chưa tồn tại
            val newId = "sv${String.format("%03d", _students.size + 1)}"
            val email = _currentStudentName.lowercase().replace(" ", "") + "@student.edu.vn"
            existingStudent = Student(newId, _currentStudentName, email)
            _students.add(existingStudent)
        }

        // Mượn sách
        bookIds.forEach { bookId ->
            val book = _books.find { it.id == bookId }
            if (book != null && book.isAvailable) {
                book.isAvailable = false
                book.borrowedBy = existingStudent.id
                existingStudent.borrowedBooks.add(bookId)
            }
        }

        return existingStudent
    }

    fun returnBook(bookId: String, studentId: String): Boolean {
        val book = _books.find { it.id == bookId }
        val student = _students.find { it.id == studentId }

        if (book != null && student != null && !book.isAvailable && book.borrowedBy == studentId) {
            book.isAvailable = true
            book.borrowedBy = null
            student.borrowedBooks.remove(bookId)
            return true
        }
        return false
    }

    fun getCurrentStudentBorrowedBooks(): List<Book> {
        val currentStudent = _students.find { it.name == _currentStudentName }
        return if (currentStudent != null) {
            _books.filter { book -> currentStudent.borrowedBooks.contains(book.id) }
        } else {
            emptyList()
        }
    }
}

// ViewModel
class LibraryViewModel : ViewModel() {
    private val repository = LibraryRepository()

    var books by mutableStateOf(repository.books)
        private set

    var students by mutableStateOf(repository.students)
        private set

    var currentStudentName by mutableStateOf(repository.currentStudentName)
        private set

    fun updateCurrentStudentName(name: String) {
        repository.setCurrentStudentName(name)
        currentStudentName = repository.currentStudentName
    }

    fun borrowBooks(bookIds: Set<String>): Boolean {
        val borrowedStudent = repository.borrowBooks(bookIds)
        if (borrowedStudent != null) {
            books = repository.books
            students = repository.students
            return true
        }
        return false
    }

    fun returnBook(bookId: String, studentId: String): Boolean {
        val success = repository.returnBook(bookId, studentId)
        if (success) {
            books = repository.books
            students = repository.students
        }
        return success
    }

    fun getCurrentStudentBorrowedBooks(): List<Book> {
        return repository.getCurrentStudentBorrowedBooks()
    }
}

// Main Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LibraryApp()
                }
            }
        }
    }
}

@Composable
fun LibraryApp() {
    val viewModel: LibraryViewModel = viewModel()
    MainScreen(viewModel = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: LibraryViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Quản lý") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Book, contentDescription = null) },
                    label = { Text("Ds sách") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Sinh viên") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> ManagementTab(viewModel, paddingValues)
            1 -> BooksTab(viewModel, paddingValues)
            2 -> StudentsTab(viewModel, paddingValues)
        }
    }
}

@Composable
fun ManagementTab(
    viewModel: LibraryViewModel,
    paddingValues: PaddingValues
) {
    var newStudentName by remember { mutableStateOf(viewModel.currentStudentName) }
    var selectedBooks by remember { mutableStateOf(setOf<String>()) }

    // Cập nhật tên sinh viên khi currentStudentName thay đổi
    LaunchedEffect(viewModel.currentStudentName) {
        newStudentName = viewModel.currentStudentName
    }

    // Lấy danh sách sách có sẵn (chưa được ai mượn)
    val availableBooks = viewModel.books.filter { it.isAvailable }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        // Header - Hệ thống Quản lý Thư viện
        Text(
            text = "Hệ thống\nQuản lý Thư viện",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        )

        // Sinh viên Section
        Text(
            text = "Tên sinh viên mượn sách",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = newStudentName,
                onValueChange = { newStudentName = it },
                placeholder = { Text("Nhập tên sinh viên") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            Button(
                onClick = {
                    if (newStudentName.isNotBlank()) {
                        viewModel.updateCurrentStudentName(newStudentName)
                    }
                },
                enabled = newStudentName.isNotBlank() && newStudentName != viewModel.currentStudentName,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                )
            ) {
                Text("Thay đổi")
            }
        }

        // Hiển thị tên sinh viên hiện tại
        Text(
            text = "Sinh viên hiện tại: ${viewModel.currentStudentName}",
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color(0xFF1976D2),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Danh sách sách Section
        Text(
            text = "Danh sách sách",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF5F5F5)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (availableBooks.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Không có sách nào có sẵn",
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Tất cả sách đã được mượn",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(availableBooks) { book ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedBooks.contains(book.id),
                                onCheckedChange = { isChecked ->
                                    selectedBooks = if (isChecked) {
                                        selectedBooks + book.id
                                    } else {
                                        selectedBooks - book.id
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = book.title,
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // Mượn sách Button
        Button(
            onClick = {
                if (viewModel.currentStudentName.isNotBlank() && selectedBooks.isNotEmpty()) {
                    val success = viewModel.borrowBooks(selectedBooks)
                    if (success) {
                        selectedBooks = setOf() // Reset selection
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedBooks.isNotEmpty() && viewModel.currentStudentName.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = if (selectedBooks.isEmpty()) "Mượn sách" else "Mượn sách (${selectedBooks.size})",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun BooksTab(
    viewModel: LibraryViewModel,
    paddingValues: PaddingValues
) {
    // Lấy danh sách sách đã mượn của sinh viên hiện tại
    val borrowedBooks = viewModel.getCurrentStudentBorrowedBooks()
    val currentStudent = viewModel.students.find { it.name == viewModel.currentStudentName }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text(
            text = "Sách đã mượn",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Sinh viên: ${viewModel.currentStudentName}",
            fontSize = 14.sp,
            color = Color(0xFF1976D2),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (borrowedBooks.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Sinh viên này chưa mượn quyển sách nào",
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Hãy vào tab 'Quản lý' để mượn sách!",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(borrowedBooks) { book ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = book.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "Tác giả: ${book.author}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = book.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // Hàng chứa button trả sách, căn phải và đều nhau
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = {
                                        currentStudent?.let { student ->
                                            viewModel.returnBook(book.id, student.id)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFE53E3E)
                                    ),
                                    modifier = Modifier.width(100.dp)
                                ) {
                                    Text("Trả sách", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentsTab(
    viewModel: LibraryViewModel,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        items(viewModel.students) { student ->
            StudentItem(student = student)
        }
    }
}

@Composable
fun StudentItem(student: Student) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = student.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "ID: ${student.id}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Email: ${student.email}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Sách đã mượn: ${student.borrowedBooks.size}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Blue
            )
        }
    }
}

@Composable
fun LibraryManagementTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF1976D2),
            secondary = Color(0xFF03DAC6)
        ),
        content = content
    )
}