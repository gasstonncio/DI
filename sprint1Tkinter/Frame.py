import tkinter as tk

def mostrar_entry():
    #Metodo para mostrar el contenido del entry dentro del segundo frame
    etiqueta_mostrar.config(text=str(entry_fr.get()))

def eliminar_entry():
    #Metodo para eliminar el contenido del entry y la etiqueta que muestra el mismo
    boton_eliminar.config(entry_fr.delete(0,len(entry_fr.get())))
    etiqueta_mostrar.destroy()

#Abrimos la ventana
root=tk.Tk()
root.title("Ejercicio 8")
root.geometry("500x450")

#Creamos el primer frame
frame1=tk.Frame(root, bg="white", bd=3, relief="groove")
frame1.pack(padx=20, pady=20,fill='both',expand=True)

#Etiquetas y boton del primer frame, para que salga dentro, sustituimos root por el frame correspondiente
etiqueta1=tk.Label(frame1, text="Primera etiqueta dentro del frame", bg="white")
etiqueta1.pack(pady=10)
etiqueta2=tk.Label(frame1, text="Segunda etiqueta dentro del frame, escribe algo en el siguiente recuadro:",bg="white")
etiqueta2.pack(pady=10)
entry_fr=tk.Entry(frame1, width=30, bg="lightgrey")
entry_fr.pack(pady=10)

#Segundo frame y su contenido
frame2=tk.Frame(root, bg="lightgrey", bd=3, relief="raised")
frame2.pack(padx=20, pady=20,fill='both',expand=True)
boton_mostrar=tk.Button(frame2, text="Mostrar", command=mostrar_entry)
boton_mostrar.pack(pady=10)
boton_eliminar=tk.Button(frame2, text= "Eliminar", command=eliminar_entry)
boton_eliminar.pack(pady=10)

#Etiqueta que muestra el contenido del entry
etiqueta_mostrar=tk.Label(frame2, text="", bg="lightgrey")
etiqueta_mostrar.pack(pady=10)


root.mainloop()