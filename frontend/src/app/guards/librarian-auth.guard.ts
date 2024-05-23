import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {UserService} from "../service/user.service";
import {catchError, map, of} from "rxjs";

export const librarianAuthGuard: CanActivateFn = (route, state) => {
  const userService = inject(UserService);
  const router = inject(Router);

  return userService.getPrincipal().pipe(
    map(value => {
      return value.libraryId != null;
    }),
    catchError(err => {
      router.navigate(['/login']);
      return of(false);
    })
  );
};
