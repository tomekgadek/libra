import { useEffect, useRef, useState } from "react";
import "react-toastify/dist/ReactToastify.css";
import Header from "./components/Header";
import BookList from "./components/BookList";
import { getBooks, saveBook, uploadCover, deleteBook } from "./api/BookService";
import { Routes, Route, Navigate } from "react-router-dom";
import BookDetail from "./components/BookDetail";
import { toastError } from "./api/ToastService";
import { ToastContainer } from "react-toastify";

function App() {
  const modalRef = useRef();
  const fileRef = useRef();
  const [data, setData] = useState({});
  const [currentPage, setCurrentPage] = useState(0);
  const [file, setFile] = useState(undefined);
  const [values, setValues] = useState({
    title: '',
    author: '',
    isbn: '',
    publisher: '',
    publishingYear: '',
    status: 'Dostępna',
  });

  const getAllBooks = async (page = 0, size = 6) => {
    try {
      setCurrentPage(page);
      const { data } = await getBooks(page, size);
      setData(data);
      console.log('data!: ', data);
    } catch (error) {
      console.log(error);
      toastError(error.message);
    }
  };

  const onChange = (event) => {
    setValues({ ...values, [event.target.name]: event.target.value });
  };

  const handleNewBook = async (event) => {
    event.preventDefault();
    try {
      const { data } = await saveBook(values);

      if (file) {
        const formData = new FormData();
        formData.append('file', file, file.name);
        formData.append('id', data.id);
        await uploadCover(formData);
      }

      toggleModal(false);
      setFile(undefined);
      if (fileRef.current) fileRef.current.value = null;
      setValues({
        title: '',
        author: '',
        isbn: '',
        publisher: '',
        publishingYear: '',
        status: 'Dostępna',
      });
      getAllBooks();
    } catch (error) {
      toastError(error.message);
    }
  };

  const handleUpdateBook = async (book) => {
    try {
      const { data } = await saveBook(book);
      console.log(data);
    } catch (error) {
      console.log(error);
      toastError(error.message);
    }
  };

  const handleUpdateCover = async (formData) => {
    try {
      await uploadCover(formData);
    } catch (error) {
      console.log(error);
      toastError(error.message);
    }
  };

  const handleDeleteBook = async (id) => {
    try {
      await deleteBook(id);
    } catch (error) {
      console.log(error);
      toastError(error.message);
    }
  };

  const toggleModal = show => show ? modalRef.current.showModal() : modalRef.current.close();

  useEffect(() => {
    getAllBooks();
  }, []);

  return (
    <>
      <Header toggleModal={toggleModal} nbOfBooks={data.totalElements} />
      <main className="main">
        <div className="container">
          <Routes>
            <Route path="/" element={<Navigate to={"/books"} />} />
            <Route path="/books" element={<BookList data={data} currentPage={currentPage} getAllBooks={getAllBooks} />} />
            <Route path="/books/:id" element={<BookDetail updateBook={handleUpdateBook} updateCover={handleUpdateCover} deleteBook={handleDeleteBook} getAllBooks={getAllBooks} />} />
          </Routes>
        </div>
      </main>

      {/* Modal */}
      <dialog ref={modalRef} className="modal" id="modal">
        <div className="modal__header">
          <h3>Nowa książka</h3>
          <i onClick={() => toggleModal(false)} className="bi bi-x-lg"> </i>
        </div>
        <div className="divider"></div>
        <div className="modal__body">
          <form onSubmit={handleNewBook}>
            <div className="user-details">
              <div className="input-box">
                <span className="details">Tytuł</span>
                <input
                  type="text"
                  value={values.title}
                  onChange={onChange}
                  name="title"
                  required
                />
              </div>
              <div className="input-box">
                <span className="details">Autor</span>
                <input
                  type="text"
                  value={values.author}
                  onChange={onChange}
                  name="author"
                  required
                />
              </div>
              <div className="input-box">
                <span className="details">ISBN</span>
                <input
                  type="text"
                  value={values.isbn}
                  onChange={onChange}
                  name="isbn"
                  required
                />
              </div>
              <div className="input-box">
                <span className="details">Wydawnictwo</span>
                <input
                  type="text"
                  value={values.publisher}
                  onChange={onChange}
                  name="publisher"
                  required
                />
              </div>
              <div className="input-box">
                <span className="details">Rok wydania</span>
                <input
                  type="text"
                  value={values.publishingYear}
                  onChange={onChange}
                  name="publishingYear"
                  required
                />
              </div>
              <div className="input-box">
                <span className="details">Status</span>
                <select
                  value={values.status}
                  onChange={onChange}
                  name="status"
                  required
                  className="form-control"
                >
                  <option value="Dostępna">Dostępna</option>
                  <option value="Niedostępna">Niedostępna</option>
                </select>
              </div>
              <div className="file-input">
                <span className="details">Okładka</span>
                <input
                  type="file"
                  onChange={(event) => setFile(event.target.files[0])}
                  ref={fileRef}
                  name="cover"
                  accept="image/*"
                />
              </div>
            </div>
            <div className="form_footer">
              <button
                onClick={() => toggleModal(false)}
                type="button"
                className="btn btn-danger"
              >
                Anuluj
              </button>
              <button type="submit" className="btn">
                Zapisz
              </button>
            </div>
          </form>
        </div>
      </dialog>
      <ToastContainer />
    </>
  );
}

export default App;
