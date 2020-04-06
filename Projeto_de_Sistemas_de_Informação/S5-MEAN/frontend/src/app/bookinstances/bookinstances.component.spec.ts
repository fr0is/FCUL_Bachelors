import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookinstancesComponent } from './bookinstances.component';

describe('BookinstancesComponent', () => {
  let component: BookinstancesComponent;
  let fixture: ComponentFixture<BookinstancesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookinstancesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookinstancesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
