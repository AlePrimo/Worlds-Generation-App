import React, { useState } from 'react'

export default function WorldForm({ onCreate }) {
  const [name, setName] = useState('')

  const submit = async (e) => {
    e.preventDefault()
    if (!name.trim()) return
    await onCreate({ name })
    setName('')
  }

  return (
    <form className="card" onSubmit={submit}>
      <h4>Crear Mundo</h4>
      <div className="form-field">
        <label>Nombre</label>
        <input value={name} onChange={e => setName(e.target.value)} />
      </div>
      <button className="button small" type="submit">Crear</button>
    </form>
  )
}
