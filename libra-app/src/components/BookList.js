import React from 'react'
import Book from './Book'

const BookList = ({ data, currentPage, getAllBooks }) => {
  return (
    <main className='main'>
        {data?.content?.length === 0 && <div className='empty-message'>Brak książek. Dodaj nową książkę.</div>}

    <ul className='book__list'>
        {data?.content?.length > 0 && data.content.map(book => <Book book={book} key={book.id} />)}
    </ul>

    {data?.content?.length > 0 && data?.totalPages > 1 &&   
        <div className='pagination'>
            <a onClick={() => getAllBooks(currentPage - 1)} className={0 === currentPage ? 'disabled' : ''}>&laquo;</a>

            { data && [...Array(data.totalPages).keys()].map((page, index) => 
              <a onClick={() => getAllBooks(page)} className={currentPage === page ? 'active' : ''} key={page}>{page + 1}</a>)
            }

            <a onClick={() => getAllBooks(currentPage + 1)} className={data.totalPages === currentPage + 1 ? 'disabled' : ''}>&raquo;</a>
        </div>
    }
    </main>
  )
}

export default BookList
