import React from 'react'

const Header = ({ toggleModal, nbOfBooks }) => {
  return (
    <header className='header'>
        <div className='container'>
            <h3><i className='bi bi-book'></i> Libra <span className='header__count'>({nbOfBooks})</span></h3>
            <button onClick={() => toggleModal(true)} className='btn'>
                <i className='bi bi-plus-square'></i> Dodaj książkę
            </button>
        </div>
    </header>
  )
}

export default Header