import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'imageBaseUrl',
  standalone: true
})
export class ImageBaseUrlPipe implements PipeTransform {

  transform(value: string): string {
    if (value && value.startsWith('/upload')) {
      return 'http://localhost:8080' + value;
    }
    return value;
  }

}
