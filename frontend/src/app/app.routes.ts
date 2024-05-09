import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './admin-layout/admin-layout.component';
import { AuthComponent } from './auth/auth.component';
import { BookService } from './service/book.service';
import { BooksListingComponent } from './books-listing/books-listing.component';
import { BookCardComponent } from './book-card/book-card.component';
import { UserLayoutComponent } from './user-layout/user-layout.component';
import { SearchComponent } from './search/search.component';
import { ListBooksComponent } from './list-books/list-books.component';
import { BookDetailsComponent } from './book-details/book-details.component';

export const routes: Routes = [
    {
        path: 'login',
        component: AuthComponent,
    },
    {
        path: 'admin',
        component: AdminLayoutComponent,
        children: [
            {
                path: 'books',
                component: BooksListingComponent
            }
        ]
    },
    {
        path: '',
        component: AdminLayoutComponent
    },
    {
        path: 'book',
        component: BookCardComponent
    },
    {
        path: 'user',
        component: UserLayoutComponent,
        children: [
            {
                path: 'books/:letter',
                component: ListBooksComponent,
            },
            {
                path: 'books/details/:id',
                component: BookDetailsComponent
            }
        ]
    },
    {
        path: 'search',
        component: SearchComponent
    },
    {
        path: 'book/details',
        component: BookDetailsComponent
    }
    
];
