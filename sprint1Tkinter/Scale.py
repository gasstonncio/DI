import tkinter as tk

def actualizar_valor(val):
    #Metodo que actualiza el valor del scale segun se mueva
    etiqueta.config(text=f"Valor {val}")

#Abrimos ventana
root = tk.Tk()
root.title("Ejercicio 11")
root.geometry("300x300")

#Creamos scale, en nuestro caso veritcal, y la posicionamos con grid en la columna 0
scale=tk.Scale(root, from_=0, to=100, orient='vertical', command=actualizar_valor)
scale.grid(row=0, column=0, pady=50, padx=50)

#Creamos etiqueta donde se mostrara el valor del scale cuando se mueva, lo ponemos en la columna 1 para que aparexca al lado del scale
etiqueta = tk.Label(root, text="")
etiqueta.grid(row=0, column=1)

root.mainloop()