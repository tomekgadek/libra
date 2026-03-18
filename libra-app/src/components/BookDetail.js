import React, { useState, useEffect, useRef } from "react";
import { Link, useParams } from "react-router-dom";
import { getBook } from "../api/BookService";
import { toastError, toastSuccess } from "../api/ToastService";

const BookDetail = ({ updateBook, updateCover, deleteBook, getAllBooks }) => {
  const inputRef = useRef();

  const [book, setBook] = useState({
    id: "",
    title: "",
    author: "",
    isbn: "",
    publisher: "",
    publishingYear: "",
    status: "",
    coverUrl: "",
  });

  const { id } = useParams();

  const fetchBook = async (id) => {
    try {
      const { data } = await getBook(id);
      setBook(data);
      console.log(data);
    } catch (error) {
      console.log(error);
      toastError(error.message);
    }
  };

  const selectImage = () => {
    inputRef.current.click();
  };

  const handleUpdateCover = async (file) => {
    try {
      const formData = new FormData();
      formData.append("file", file, file.name);
      formData.append("id", id);
      await updateCover(formData);

      setBook((prev) => ({
        ...prev,
        coverUrl: `${prev.coverUrl}?updated_at=${new Date().getTime()}`,
      }));
      toastSuccess("Okładka zaktualizowana");
    } catch (error) {
      console.log(error);
      toastError(error.message);
    }
  };

  const onChange = (event) => {
    setBook({ ...book, [event.target.name]: event.target.value });
  };

  const onUpdateBook = async (event) => {
    event.preventDefault();
    await updateBook(book);
    getAllBooks();
    fetchBook(id);
    toastSuccess("Książka zaktualizowana");
  };

  const onDeleteBook = async () => {
    if (window.confirm("Czy na pewno chcesz usunąć tę książkę?")) {
      await deleteBook(id);
      getAllBooks();
      toastSuccess("Książka usunięta");
      window.location.href = "/";
    }
  };

  useEffect(() => {
    fetchBook(id);
  }, []);

  return (
    <>
      <Link to={"/"} className="link">
        <i className="bi bi-arrow-left"></i> Powrót do listy
      </Link>
      <div className="profile">
        <div className="profile__details">
          <img
            src={book.coverUrl || '/img/no-cover.png'}
            alt={`Okładka: ${book.title}`}
          />
          <div className="profile__metadata">
            <p className="profile__name">{book.title}</p>
            <p className="profile__muted">JPG, GIF lub PNG. Maks. 10MB</p>
            <button onClick={selectImage} className="btn">
              <i className="bi bi-cloud-upload"></i> Zmień okładkę
            </button>
          </div>
        </div>
        <div className="profile__settings">
          <div>
            <form onSubmit={onUpdateBook} className="form">
              <div className="user-details">
                <input
                  type="hidden"
                  defaultValue={book.id}
                  name="id"
                  required
                />
                <div className="input-box">
                  <span className="details">Tytuł</span>
                  <input
                    type="text"
                    value={book.title}
                    onChange={onChange}
                    name="title"
                    required
                  />
                </div>
                <div className="input-box">
                  <span className="details">Autor</span>
                  <input
                    type="text"
                    value={book.author}
                    onChange={onChange}
                    name="author"
                    required
                  />
                </div>
                <div className="input-box">
                  <span className="details">ISBN</span>
                  <input
                    type="text"
                    value={book.isbn}
                    onChange={onChange}
                    name="isbn"
                    required
                  />
                </div>
                <div className="input-box">
                  <span className="details">Wydawnictwo</span>
                  <input
                    type="text"
                    value={book.publisher}
                    onChange={onChange}
                    name="publisher"
                    required
                  />
                </div>
                <div className="input-box">
                  <span className="details">Rok wydania</span>
                  <input
                    type="text"
                    value={book.publishingYear}
                    onChange={onChange}
                    name="publishingYear"
                    required
                  />
                </div>
                <div className="input-box">
                  <span className="details">Status</span>
                  <select
                    value={book.status}
                    onChange={onChange}
                    name="status"
                    required
                    className="form-control"
                  >
                    <option value="Dostępna">Dostępna</option>
                    <option value="Niedostępna">Niedostępna</option>
                  </select>
                </div>
              </div>
              <div className="form_footer">
                <button 
                  type="button" 
                  onClick={onDeleteBook} 
                  className="btn btn-danger"
                >
                  Usuń książkę
                </button>
                <button type="submit" className="btn">
                  Zapisz
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <form style={{ display: "none" }}>
        <input
          type="file"
          ref={inputRef}
          onChange={(event) => handleUpdateCover(event.target.files[0])}
          name="file"
          accept="image/*"
        />
      </form>
    </>
  );
};

export default BookDetail;
