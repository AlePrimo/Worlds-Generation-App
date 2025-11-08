import React from 'react'

export default function RegionList({ regions = [], onDelete }) {
  return (
    <div className="card">
      <h4>Regiones</h4>
      <div className="list">
        {regions.map(r => (
          <div key={r.id} style={{display:'flex', justifyContent:'space-between', alignItems:'center'}}>
            <div>
              <strong>{r.name}</strong> <span style={{color:'#6b7280'}}>alive: {r.alive ? 'sí' : 'no'}</span>
            </div>
            <div className="row">
              <button className="button small" onClick={() => onDelete(r.id)}>Eliminar</button>
            </div>
          </div>
        ))}
        {regions.length === 0 && <div className="notice">No hay regiones</div>}
      </div>
    </div>
  )
}
