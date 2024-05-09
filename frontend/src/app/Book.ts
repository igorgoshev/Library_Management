export interface Book {
    isbn: string,
    name: string,
    description: string, 
    imgUrl: string,
    publisher: string,
    id: number,
    categories: string[],
    authors: string[],
    averageRating: number,
    numPages: number
}