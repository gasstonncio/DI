import tkinter as tk
from tkinter import messagebox

#Crear ventana
root=tk.Tk()
root.title("Ejercicio 7")
root.geometry("600x600")

#Creamos el cuadro donde se dibujaran los circulos: Canvas
canvas=tk.Canvas(root, width=400, height= 400, bg="black")
canvas.pack(pady=20)

#Metodo para recoger los valores inntroducidos por teclado que nos daran las coordenadas del circulo
def print_oval():
    try:
        canvas.create_oval(100, 100, 100 + float(entry_x.get()), 100 + float(entry_y.get()), outline="pink")
    except ValueError:
        messagebox.showerror("Error","Solo se aceptaran caracteres numericos")

#Etiquetas y entrys para recoger por teclado los valores de las coordenadas
etiqueta_x = tk.Label(root, text="Eje x (anchura):")
etiqueta_x.pack()
entry_x = tk.Entry(root, width=10)
entry_x.pack()
etiqueta_y = tk.Label(root, text="Eje y (altura):")
etiqueta_y.pack()
entry_y = tk.Entry(root, width=10)
entry_y.pack()

#Boton para pintar el circulo una vez metidos los valores. Invoca al metodo
boton=tk.Button(root, text="Dibujar", command=print_oval)
boton.pack()

root.mainloop()