import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './admin-layout/admin-layout.component';
import { AuthComponent } from './auth/auth.component';
import { BookService } from './service/book.service';
import { BooksListingComponent } from './books-listing/books-listing.component';

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
];
