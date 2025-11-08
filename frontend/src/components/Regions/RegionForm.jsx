import React, { useState, useEffect } from 'react'
import { worldsApi } from '../../services/api'

export default function RegionForm({ onCreate }) {
  const [form, setForm] = useState({
    name: '',
    lat: 0,
    lon: 0,
    population: 100,
    water: 50,
    food: 50,
    minerals: 50,
    worldName: ''
  })
  const [worlds, setWorlds] = useState([])

  useEffect(() => {
    worldsApi.list().then(ws => {
      setWorlds(ws)
      if (ws.length > 0 && !form.worldName) {
        setForm(f => ({ ...f, worldName: ws[0].name }))
      }
    })
  }, [])

  const handleChange = e => {
    const { name, value, type } = e.target
    setForm(f => ({
      ...f,
      [name]: type === 'number'
        ? (value === '' ? 0 : parseFloat(value))
        : value
    }))
  }

  const submit = async e => {
    e.preventDefault()
    if (!form.name.trim()) {
      alert('El nombre es obligatorio.')
      return
    }
    if (!form.worldName) {
      alert('Debe seleccionar un mundo.')
      return
    }

    const payload = {
      name: form.name.trim(),
      lat: form.lat,
      lon: form.lon,
      population: form.population,
      water: form.water,
      food: form.food,
      minerals: form.minerals,
      worldName: form.worldName
    }

    try {
      await onCreate(payload)
      setForm(f => ({ ...f, name: '' }))
    } catch (err) {
      console.error(err)
      alert('Error al crear región')
    }
  }

  return (
    <form className="card" onSubmit={submit}>
      <h4>Crear Región</h4>

      <div className="form-field">
        <label>Nombre</label>
        <input name="name" value={form.name} onChange={handleChange} />
      </div>

      <div className="form-field">
        <label>Lat</label>
        <input name="lat" type="number" value={form.lat} onChange={handleChange} />
      </div>

      <div className="form-field">
        <label>Lon</label>
        <input name="lon" type="number" value={form.lon} onChange={handleChange} />
      </div>

      <div className="form-field">
        <label>Población</label>
        <input name="population" type="number" value={form.population} onChange={handleChange} />
      </div>

      <div className="form-field">
        <label>Agua</label>
        <input name="water" type="number" value={form.water} onChange={handleChange} />
      </div>

      <div className="form-field">
        <label>Alimento</label>
        <input name="food" type="number" value={form.food} onChange={handleChange} />
      </div>

      <div className="form-field">
        <label>Minerales</label>
        <input name="minerals" type="number" value={form.minerals} onChange={handleChange} />
      </div>

      <div className="form-field">
        <label>Mundo</label>
        <select name="worldName" value={form.worldName} onChange={handleChange}>
          {worlds.map(w => (
            <option key={w.id} value={w.name}>{w.name}</option>
          ))}
        </select>
      </div>

      <div className="row">
        <button className="button small" type="submit">Crear Región</button>
      </div>
    </form>
  )
}
