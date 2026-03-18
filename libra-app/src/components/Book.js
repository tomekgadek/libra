import React from 'react'
import { Link } from 'react-router-dom'

const Book = ({ book }) => {
  return (
    <Link to={`/books/${book.id}`} className='book__item'>
        <div className='book__header'>
            <div className='book__image'>
                <img src={book.coverUrl ? `${book.coverUrl}?updated_at=${new Date().getTime()}` : '/img/no-cover.png'} alt={book.title} />
            </div>
            <div className='book__details'>
                <p className='book_title'>{book.title && book.title.substring(0, 30)}</p>
                <p className='book_author'>{book.author}</p>
            </div>
        </div>
        <div className='book__body'>
            <p><i className='bi bi-person'></i> {book.author}</p>
            <p><i className='bi bi-upc-scan'></i> {book.isbn}</p>
            <p><i className='bi bi-building'></i> {book.publisher}</p>
            <p><i className='bi bi-calendar'></i> {book.publishingYear}</p>
            <p>{book.status === 'Dostępna' ? <i className='bi bi-check-circle'></i> : 
                <i className='bi bi-x-circle'></i>} {book.status}</p>
        </div>
    </Link>
  )
}

export default Book
