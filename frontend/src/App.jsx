import { useState } from 'react'
import Login from './pages/Login';

function App() {

  const [token, setToken] = useState(null);
  
  function handleAuthed(t){

    setAuthToken(t);
    setToken(t);
  }

  if(!token){
    return <Login onAuthed = {handleAuthed} />
  }

  
  return (
    <div className='min-h-screen bg-slate-900 flex items-center justify-center'>

      <h1 className='text-white text-2xl '> Logged In Token {token.slice(0, 20)}... </h1>
      
    </div>
  )
}

export default App
